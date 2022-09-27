package com.kk.feign_api.client;

import com.kk.feign_api.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter")
public interface UCenterClient {
    @GetMapping(value = "/api/ucenter/member/inner/get-member-dto/{memberId}")
    public abstract MemberDto getMemberDtoByMemberId(@PathVariable(value = "memberId") String memberId);
}
