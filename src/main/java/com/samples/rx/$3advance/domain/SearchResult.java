package com.samples.rx.$3advance.domain;

import com.samples.rx.$1basics.domain.Account;

import java.util.Map;

public class SearchResult {

    private Account account;
    private Map<String, String> metadata;

    public SearchResult(Account account, Map<String, String> metadata) {
        this.account = account;
        this.metadata = metadata;
    }

    public Account getAccount() {
        return account;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "account=" + account +
                ", metadata=" + metadata +
                '}';
    }
}
