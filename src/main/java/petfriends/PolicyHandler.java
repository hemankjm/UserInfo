package petfriends;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import petfriends.config.KafkaProcessor;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    // @StreamListener(KafkaProcessor.INPUT)
    // public void wheneverWalkEnded_(@Payload WalkEnded walkEnded){

    // 	if(walkEnded.isMe()){
    //         System.out.println("######## listener  : " + walkEnded.toJson());

    //         //List<UserInfo> list = userInfoRepository.findByOrderId(orderCancelled.getId()); // mariadb  추가하면서 주석

    //         //for(UserInfo userInfo : list){
    //         	// userInfo.setCancelYn("Y"); // 테이블 변경하면서 주석처리 2202.06.27
    //             // view 객체에 이벤트의 eventDirectValue 를 set 함
    //             // view 레파지 토리에 save
    //         //	userInfoRepository.save(userInfo);
    //         //}

    //     }
    // }

}
