import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String[] args) {
		try {
			// 서버 객체를 생성한다.
			ServerSocket serverSocket = new ServerSocket(8888);
			// 사용자 접속을 대기한다.
			System.out.println("사용자 접속 대기");
			Socket socket = serverSocket.accept();
			System.out.println(socket);
			
			// 클라이언트에게 데이터를 전달한다.
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream =  new DataOutputStream(outputStream);
			
			dataOutputStream.writeInt(100);
			dataOutputStream.writeDouble(11.11);
			dataOutputStream.writeBoolean(true);
			dataOutputStream.writeUTF("서버가 보낼 문자열");
			
			// 클라이언트로 부터 데이터를 수신 받는다.
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			
			int a1 = dataInputStream.readInt();
			double a2 = dataInputStream.readDouble();
			boolean a3 = dataInputStream.readBoolean();
			String a4 = dataInputStream.readUTF();
			
			
			System.out.println("a1 : "+ a1);
			System.out.println("a2 : "+ a2);
			System.out.println("a3 : "+ a3);
			System.out.println("a4 : "+ a4);
			
			socket.close();
			serverSocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
