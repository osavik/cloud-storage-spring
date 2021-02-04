package com.udacity.jwdnd.course1.cloudstorage.service.facade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
