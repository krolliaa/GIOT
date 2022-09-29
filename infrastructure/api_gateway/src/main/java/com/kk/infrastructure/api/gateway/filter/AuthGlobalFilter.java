package com.kk.infrastructure.api.gateway.filter;

import com.google.gson.JsonObject;
import com.kk.common.util.JWTUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            //获取请求头中的 token
            List<String> tokenList = request.getHeaders().get("token");
            // 这种写法是为了防止写误，编译器可以检测出 null =
            if(null == tokenList) return out(exchange.getResponse());
            //校验Token
            boolean isCheck = JWTUtils.checkedJWTToken(tokenList.get(0));
            if(!isCheck) return out(exchange.getResponse());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "");
        message.addProperty("message", "该操作需要登录，请登陆后再操作");
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = (DataBuffer) response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //输出http响应
        return response.writeWith(Mono.just(dataBuffer));
    }
}