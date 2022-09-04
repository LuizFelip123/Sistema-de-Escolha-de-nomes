package br.edu.ifba.saj.ads.web.estoque.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserGoogle  implements OAuth2User{
    
    private OAuth2User auth2User;

    public OAuth2UserGoogle(OAuth2User auth2User){
        this.auth2User = auth2User;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return auth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return auth2User.getName();
    }
    
    public String getEmail() {
        return auth2User.getAttribute("email");
    }
    
    public String getPicture() {
        return auth2User.getAttribute("picture");
    }
    
}
