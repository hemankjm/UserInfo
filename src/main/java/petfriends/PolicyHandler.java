package petfriends;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import petfriends.config.KafkaProcessor;

import petfriends.userInfo.dto.PointChanged;
import petfriends.userInfo.dto.StarScoreGranted;
import petfriends.userInfo.dto.WalkEnded;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    UserInfoRepository userInfoRepository;


    /**
     * 결재의 포인트 변경 비동기 처리
     * @param pointChanged
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPointChanged_(@Payload PointChanged pointChanged){

    	if(pointChanged.isMe()){
            System.out.println("######## pointChanged listener  : " + pointChanged.toJson());

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(pointChanged.getUserId());
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setPointAmount(pointChanged.getPoint()); // 포인트 갱신
            userInfoRepository.save(userInfo);

        }
    }

    /**
     * 일지의 별점 등록 비동기 처리
     * @param starScoreGranted
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverStarScoreGranted_(@Payload StarScoreGranted starScoreGranted){

    	if(starScoreGranted.isMe()){
            System.out.println("######## starScoreGranted listener  : " + starScoreGranted.toJson());

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(starScoreGranted.getDogWalkerId()); //도그위커의 별점을 갱신한다
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setWalkCount(userInfo.getWalkCount() + 1); // 산책 횟수 증가
            userInfo.setAvgScore(starScoreGranted.getStarScore() / userInfo.getWalkCount()); // 평점 갱신
            userInfoRepository.save(userInfo);

        }
    }


    /**
     * 산책 종료의 비동기 처리
     * @param starScoreGranted
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkEnded_(@Payload WalkEnded walkEnded){

    	if(walkEnded.isMe()){
            System.out.println("######## walkEnded listener  : " + walkEnded.toJson());

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(walkEnded.getUserId()); //도그위커의 별점을 올려준다
            UserInfo userInfo = userInfoOptional.get();

            Optional<Double> optional = Optional.ofNullable(userInfo.getUseCount());
            Double useCount = optional.orElse((double)0); // 값이 없다면 0 을 리턴

            userInfo.setWalkCount(useCount + 1); // 이용 횟수 증가
            userInfoRepository.save(userInfo);

        }
    }

}
