package org.techtown.socketclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.techtown.socketclient.databinding.ActivityMainBinding;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    // 접속할 서버의 IP
    String serverIP = "192.168.1.111";
    // 서버의 포트
    int portNumber = 55555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Button1ClickListener button1ClickListener = new Button1ClickListener();
        activityMainBinding.button.setOnClickListener(button1ClickListener);
    }

    // 네트워크 쓰레드
    class NetworkThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                //소켓 객체를 생성한다.
                // 서버 접속
                Socket socket = new Socket(serverIP, portNumber);
                Log.d("test", socket.toString());

                // 스트림을 추출한다.
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                int a1 = dataInputStream.readInt();
                double a2 = dataInputStream.readDouble();
                boolean a3 = dataInputStream.readBoolean();
                String a4 = dataInputStream.readUTF();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activityMainBinding.textView.setText("a1 : " + a1 + "\n");
                        activityMainBinding.textView.append("a2 : " + a2 + "\n");
                        activityMainBinding.textView.append("a3 : " + a3 + "\n");
                        activityMainBinding.textView.append("a4 : " + a4 + "\n");
                    }
                });
                // 스트림을 추출한다.
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeInt(200);
                dataOutputStream.writeDouble(22.22);
                dataOutputStream.writeBoolean(false);
                dataOutputStream.writeUTF("클라이언트가 보낸 문자열");

                // 접속 해제
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Button1ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NetworkThread thread = new NetworkThread();
            thread.start();
        }
    }
}