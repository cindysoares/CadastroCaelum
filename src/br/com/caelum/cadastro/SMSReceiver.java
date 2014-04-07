package br.com.caelum.cadastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("receiver", "chegou um sms.");
		Toast.makeText(context, "Chegou um SMS.", Toast.LENGTH_LONG).show();
		
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		
		byte[] lastMessage = (byte[]) messages[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(lastMessage);
		Log.i("receiver", "mensagem de: " + sms.getEmailFrom());
		
		
		
		
	}

}
