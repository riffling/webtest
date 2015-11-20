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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.autentia.showcase.web.rest.contacts.AppRestConstants.JSON;
import static com.autentia.showcase.web.rest.contacts.AppRestConstants.REST_SERVICES_LOCATION;

@Controller
@RequestMapping(value = REST_SERVICES_LOCATION, produces = JSON)
public class EchoController {

    private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

    @RequestMapping(value = "/echo", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String echo(@RequestParam(required = false, defaultValue = "This is the default message") String msg) {
        logger.debug("Echo message: {}", msg);
        return msg;
    }

}
