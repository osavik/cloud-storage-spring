package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // insert credential
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    // get all credentials by userid
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getAllCredentials(Integer userId);

    // get credentials by url & userId
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} AND userid = #{userId}")
    Credential getCredentialByUrlAndUserId(String url, Integer userId);

    // delete credential by credentialId
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredentialById(Integer credentialId);

    // update credential by credentialId
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}" +
            " WHERE credentialId = #{credentialId}")
    Integer updateCredentialById(Integer credentialId, String url, String username, String key, String password);

}
