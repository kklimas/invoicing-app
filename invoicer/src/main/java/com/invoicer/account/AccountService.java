package com.invoicer.account;

import com.invoicer.account.model.Account;
import com.invoicer.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }


}
