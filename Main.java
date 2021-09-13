public class Main {
    private static final int ATTEMPTS = 20;
    
    public static void main(String[] args) {
        /**
         * Conexão com o servidor
         */
        SocketClient client = new SocketClient("192.168.1.30", 5002);
        
        try {
            client.connect();
        } catch (Exception exception) {
            System.out.println("Não foi possível conectar ao servidor");
            return;
        }
        
        /**
         * Requisições ao Servidor por meio do Socket
         */
        long rttSum = 0;
        int successfulRequests = 0;
        
        for(int i = 0; i < ATTEMPTS; i++) {
            try {
                long startTime = System.currentTimeMillis();
                String response = client.send("redes com sockets");
                long endTime = System.currentTimeMillis();

                // Cálculo do RTT
                long rtt = endTime - startTime;
                rttSum += rtt;
                successfulRequests++;
                
                Thread.sleep(1000);
            } catch(Exception e) {}
        }
        
        /**
         * Encerrar comunicação com o socket
         */
        client.disconnect();
        
        /**
         * Cálculo das métricas
         */
        long avgRtt = rttSum / successfulRequests;
        float lostPackets = 1.0f * (ATTEMPTS - successfulRequests) / ATTEMPTS;
        
        System.out.println("Quantidade de requisições realizadas: " + ATTEMPTS);
        System.out.println("Quantidade de requisições bem sucedidas: " + successfulRequests);
        System.out.printf("Taxa de perda de pacotes: %.2f%%%n", lostPackets * 100);
        System.out.println("RTT médio: " + avgRtt + "ms");
    }
}
