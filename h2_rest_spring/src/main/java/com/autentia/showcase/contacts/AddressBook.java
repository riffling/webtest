/**
 *    Copyright 2013 Autentia Real Business Solution S.L.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.autentia.showcase.contacts;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AddressBook {

    private final Map<String, Contact> contacts = new ConcurrentHashMap<String, Contact>();

    public AddressBook() {
        prepareSomeFakeContacts();
    }

    private void prepareSomeFakeContacts() {
        save(new Contact("1111-1111-1111-1111", "Scott Summers", "summers@gmail.com"));
        save(new Contact("2222-2222-2222-2222", "Peter Parker", "parker@gmail.com"));
        save(new Contact("3333-3333-3333-3333", "Steve Rogers", "rogers@gmail.com"));
        save(new Contact("4444-4444-4444-4444", "Red Richards", "richards@gmail.com"));
    }

    public Collection<Contact> contacts() {
        return Collections.unmodifiableCollection(contacts.values());
    }

    public void save(Contact contact) {
        if (contact.getId().equals(Contact.NEW)) {
            contact = new Contact(generateUUID(), contact);
        }
        contacts.put(contact.getId(), contact);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public Contact load(String contactId) {
        return contacts.get(contactId);
    }

    public Collection<Contact> filterContactsBy(String name) {
        final Collection<Contact> contactsWithName = new ArrayList<Contact>();
        for (Contact contact : contacts.values()) {
            if (contact.getName().contains(name)) {
                contactsWithName.add(contact);
            }
        }
        return Collections.unmodifiableCollection(contactsWithName);
    }

}
