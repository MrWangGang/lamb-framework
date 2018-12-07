package org.lamb.framework.core.security;


import org.apache.commons.lang3.StringUtils;
import org.lamb.framework.common.exception.EventException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.regex.Pattern;

import static org.lamb.framework.common.enumeration.ExceptionEnum.*;
import static org.lamb.framework.core.contract.Contract.LAMB_AUTH_TOKEN_REGX;


/**
 * @description: AuthToken认证管理器
 * @author: Mr.WangGang
 * @create: 2018-10-19 下午 1:18
 **/
@Component
public class AuthTokenReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private LambUnifyAuthTokenHandler lambUnifyAuthTokenHandler;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        String authToken  = authorizationContext.getExchange().getRequest().getHeaders().getFirst("authToken");
        if(StringUtils.isBlank(authToken))throw new EventException(EA00000003);
        if(!(Pattern.compile(LAMB_AUTH_TOKEN_REGX).matcher(authToken).matches()))throw new EventException(EA00000007);
        if(!(lambUnifyAuthTokenHandler.hasKey(authToken)))throw new EventException(EA00000000);
        String principal = lambUnifyAuthTokenHandler.getPrincipalByToken(authToken);
        if(StringUtils.isBlank(principal))throw new EventException(EA00000000);
        String principalModel = getPrincipalModel(authToken);
        //更新用户信息到
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext==null) {
            setAuthentication(principal,authToken,principalModel);
        }else {
            Authentication authentication =  securityContext.getAuthentication();
            if(authentication==null){
                setAuthentication(principal,authToken,principalModel);
            }else {
                LambAuthTokenAuthentication lambAuthTokenAuthentication = getAuthentication();
                String pric = lambAuthTokenAuthentication.getPrincipal();
                String cert = lambAuthTokenAuthentication.getCredentials();
                if(StringUtils.isBlank(pric)){
                    setAuthentication(principal,authToken,principalModel);
                }else if(StringUtils.isBlank(cert)){
                    setAuthentication(principal,authToken,principalModel);
                }else {
                    if(!pric.equals(principal)){
                        setAuthentication(principal,authToken,principalModel);
                    }else if(!cert.equals(authToken)){
                        setAuthentication(principal,authToken,principalModel);
                    }
                }
            }
        }
        return Mono.just(new AuthorizationDecision(true));
    }

    private void setAuthentication(String principal,String authToken,String principalModel){
        SecurityContextHolder.getContext().setAuthentication(new LambAuthTokenAuthentication(principal, authToken,principalModel));
    }
    private String getPrincipalModel(String key){
        String[] str = key.split("\\.");
        if(str.length!=5)throw new EventException(EA00000007);
        if(StringUtils.isBlank(str[3]))throw new EventException(EA00000007);
        return str[3];
    }

    private LambAuthTokenAuthentication getAuthentication(){
        if(SecurityContextHolder.getContext() == null)throw new EventException(EA00000008);
        if(SecurityContextHolder.getContext().getAuthentication() == null)throw new EventException(EA00000008);
        try {
            LambAuthTokenAuthentication authentication = (LambAuthTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
            return authentication;
        }catch (Exception e){
            throw new EventException(EA00000003);
        }
    }
}
