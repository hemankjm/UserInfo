package petfriends;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import petfriends.config.KafkaProcessor;

import petfriends.userInfo.dto.PointChanged;
import petfriends.userInfo.dto.StarScoreGranted;
import petfriends.userInfo.model.UserInfo;
import petfriends.userInfo.repository.UserInfoRepository;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    UserInfoRepository userInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPointChanged_(@Payload PointChanged pointChanged){

    	if(pointChanged.isMe()){
            System.out.println("######## pointChanged listener  : " + pointChanged.toJson());

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(pointChanged.getUserId());
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setPointAmount(userInfo.getPointAmount() + pointChanged.getPoint()); // 포인트 갱신
            userInfoRepository.save(userInfo);

        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverStarScoreGranted_(@Payload StarScoreGranted starScoreGranted){

    	if(starScoreGranted.isMe()){
            System.out.println("######## starScoreGranted listener  : " + starScoreGranted.toJson());

            Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(starScoreGranted.getUserId());
            UserInfo userInfo = userInfoOptional.get();
            userInfo.setAvgScore((double)starScoreGranted.getStarScore()); // 평점 갱신
            userInfoRepository.save(userInfo);

        }
    }

}
