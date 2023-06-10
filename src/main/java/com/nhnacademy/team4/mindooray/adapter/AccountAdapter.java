package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.request.UpdateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.account.LoginResponse;
import com.nhnacademy.team4.mindooray.exception.AuthenticationNotFoundEmailException;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@Component
public class AccountAdapter {
    private final ApiProperties apiProperties;
    private final String accountUrl;

    public AccountAdapter(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
        this.accountUrl = apiProperties.getAccount();
    }

    /**
     * loginId(사용자 로그인 아이디)를 통해 사용자 정보 요청
     *
     * @param loginId 사용자 로그인 아이디
     * @return 매팽된 {@link LoginResponse} 객체
     */
    public LoginResponse getLoginInfoByLoginId(String loginId) {
        RestApiUrlBuilder<LoginResponse> build = RestApiUrlBuilder.builder()
                .url(accountUrl + "/auth/login/" + loginId)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .build();

        return RestApiUtils.getExchange(
                build,
                LoginResponse.class
        );
    }

    /**
     * 사용자 이메일을 통해 사용자 정보 요청
     *
     * @param email 사용자 이메일
     * @return 매핑된 {@link LoginResponse} 객체
     */
    public LoginResponse getAccountByEmail(String email) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(accountUrl + "/auth/login/{email}")
                .pathVariable("email", email)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .build();

        try {
            return RestApiUtils.getExchange(builder, LoginResponse.class);
        } catch (HttpClientErrorException e) {
            throw new AuthenticationNotFoundEmailException("not found email", email);
        }
    }

    /**
     * account 생성 요청
     *
     * @param createAccountRequest
     * @return 매핑된 {@link AccountResponse} 객체
     */
    public AccountResponse register(CreateAccountRequest createAccountRequest) {
        RestApiUrlBuilder<CreateAccountRequest> builder = RestApiUrlBuilder.builder()
                .url(accountUrl)
                .method(HttpMethod.POST)
                .header("Content-Type", "application/json")
                .body(createAccountRequest)
                .build();

        return RestApiUtils.getExchange(builder, AccountResponse.class);
    }

    /**
     * 로그인 성공한 어카운트의 로그인 아이디를 이용해 어카운트 아이디 요청
     * @param loginId
     * @return
     */
    public AccountResponse getAccountByLoginId(String loginId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(accountUrl + "/login/{loginId}")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("loginId", loginId)
                .build();

        return RestApiUtils.getExchange(
                builder,
                AccountResponse.class
                );
    }

    /**
     * 전체 어카운트 정보 요청
     * @return
     */
    public List<AccountResponse> getAccounts() {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(accountUrl)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .build();

        return RestApiUtils.getExchangeList(
                builder,
                AccountResponse.class
        );
    }

    /**
     * account id 로 어카운트 정보 요청
     * @param accountId
     * @return
     */
    public AccountResponse getAccountById(long accountId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(accountUrl + "/{accountId}")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("accountId", accountId)
                .build();

        return RestApiUtils.getExchange(builder, AccountResponse.class);
    }

    /**
     * account 수정
     * @param accountId
     * @param updateAccountRequest
     */
    public void updateAccount(long accountId, UpdateAccountRequest updateAccountRequest) {
        RestApiUrlBuilder<UpdateAccountRequest> builder = RestApiUrlBuilder.builder()
                .url(accountUrl + "/{accountId}")
                .method(HttpMethod.PATCH)
                .pathVariable("accountId", accountId)
                .body(updateAccountRequest)
                .build();

        RestApiUtils.getExchange(builder, AccountResponse.class);
    }
}
