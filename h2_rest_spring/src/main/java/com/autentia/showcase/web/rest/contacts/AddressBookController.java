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
package com.autentia.showcase.web.rest.contacts;

import com.autentia.showcase.contacts.AddressBook;
import com.autentia.showcase.contacts.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping(value = AppRestConstants.REST_SERVICES_LOCATION, produces = AppRestConstants.JSON)
public class AddressBookController {

    private static final Logger logger = LoggerFactory.getLogger(AddressBookController.class);

    private final AddressBook addressBook;

    @Autowired
    public AddressBookController(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Contact> getAllContacts() {
        final Collection<Contact> contacts = addressBook.contacts();
        logger.debug("Retrieving {}/{} contacts", contacts.size(), contacts.size());
        return contacts;
    }

    @RequestMapping(value = "/contacts/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContact(@PathVariable String contactId, @RequestParam(required = false, defaultValue = "false") boolean block) {
        final Contact contact = addressBook.load(contactId);
        logger.debug("Retrieving contact with id {} and block{}: {}", contactId, block, contact);
        return contact;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, params = "name")
    @ResponseBody
    public Contact[] filterContactsBy(@RequestParam String name) {
        final Collection<Contact> contacts = addressBook.filterContactsBy(name);
        logger.debug("Retrieving {}/{} contacts", contacts.size(), contacts.size());
        return contacts.toArray(new Contact[contacts.size()]);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST, consumes = AppRestConstants.JSON)
    @ResponseBody
    public void addContact() {
//    public void addContact(@RequestBody Contact contact) {
//        addressBook.save(contact);
        addressBook.save(new Contact());
    }

}
