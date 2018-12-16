package utils;

import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Jpush {

	   //Map<String, String> parm�����Լ��������Ĳ���,ͬѧ�ǿ����Զ������
	   public static void jpushAndroid(Map<String, String> parm) {
	       // ���ú��˺ŵ�app_key��masterSecret 
	       String appKey = "5382a1d59fe7d6beb865e20d";
	       String masterSecret = "c3b55ededc62b7607e6ae4b2";
	       //����JPushClient
	       JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	       //���͵Ĺؼ�,����һ��payload 
	       PushPayload payload = PushPayload.newBuilder()
	            .setPlatform(Platform.android())//ָ��androidƽ̨���û�
//	            .setAudience(Audience.alias("123456"))//����Ŀ�е������û�
	            .setAudience(Audience.all())
	            .setNotification(Notification.android(parm.get("msg"), "����title", parm))
	            //��������,���ﲻҪäĿ����ճ��,�������Ҵ�controller�����ù����Ĳ���)
	            .setOptions(Options.newBuilder().setApnsProduction(false).build())
	            //������ָ����������,��������Ҳû��ϵ
	            .setMessage(Message.content(parm.get("msg")))//�Զ�����Ϣ
	            .build();

	       try {
	            PushResult pu = jpushClient.sendPush(payload);

	        } catch (APIConnectionException e) {
	            e.printStackTrace();
	        } catch (APIRequestException e) {
	            e.printStackTrace();
	        }    
	   }
}