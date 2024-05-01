package shoppingcart.discount.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shoppingcart.discount.repository.DiscountRepository;
import shoppingcart.discount.service.DiscountService;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final DiscountRepository discountRepository;
    @Bean
    public Server grpcServer() {
        int port = 9090; // Choose your desired port number
        return NettyServerBuilder.forPort(port)
                .addService(new DiscountService(discountRepository)) // Your gRPC service implementation
                 // Use insecure connection
                .build();
    }
}
