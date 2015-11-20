package org.third.spring.bank;

public interface BankDao {
	public Account readAccount(Long id);

	public void createOrUpdateAccount(Account account);

	public Account[] findAccounts();
}
