package com.study.security.auth.service;

import com.study.security.auth.dto.OAuthAttributes;
import com.study.security.auth.dto.SessionUser;
import com.study.security.domain.member.domain.User;
import com.study.security.domain.member.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //oauth custom login
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //registerId ex: google, naver등의 platform
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //업체id, key, responseData
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(oAuthAttributes);

        //세션에 정보저장
        /*
        * User엔티티를 세션에 저장하지 않는 이유는 직렬화를 구현하지 않았다는 에러가 나기 때문이다.
        * 엔티티(User)는 직렬화 코드를 넣지 않는 것이 좋기 때문에
        * dto로 직렬화가 가능하게끔 한번 감싸서 세션에 저장하는 것이 좋다.
        * */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                //지정된 객체만을 포함하고 있는 불변세트를 만듬!
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey()))
                ,oAuthAttributes.getAttributes()
                ,oAuthAttributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes oAuthAttributes) {
        //사용자 정보에 한하는 데이터가 있을 경우 - update or entity를 만든다?
        User user = userRepository.findByEmail(oAuthAttributes.getEmail())
                .map(entity -> entity.update(oAuthAttributes.getName(), oAuthAttributes.getPicture()))
                .orElse(oAuthAttributes.toEntity());

        //저어어장,,
        return userRepository.save(user);
    }
}
