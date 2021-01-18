package com.infrastructure.callnotificationsystem.controller;

import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.service.CallHistoryService;
import com.infrastructure.callnotificationsystem.service.DeliveryHistoryService;
import com.infrastructure.callnotificationsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "REST Interface", authorizations = {@Authorization(value="basicAuth")})
@RequestMapping("/api")
@ResponseBody
@Log4j2
public class NotificationRestAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private CallHistoryService callHistoryService;

    @Autowired
    private DeliveryHistoryService deliveryHistoryService;

    @PostMapping("/user")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
            userService.createUser(userDTO);
            log.info(userDTO.getPhoneNumber() + " : user is created.");
            return userDTO;
    }

    @PostMapping("/call")
    public CallHistoryDTO createCallHistory(@Valid @RequestBody CallHistoryDTO callHistoryDTO) throws Exception {
            callHistoryService.createOrUpdateCallHistory(callHistoryDTO);
            log.info(callHistoryDTO.getCallerUser() + " -> " + callHistoryDTO.getCalledUser() + " : callHistory is created.");
            return callHistoryDTO;
    }

    @PostMapping("/delivery")
    public DeliveryHistoryDTO createDeliveryHistory(@Valid @RequestBody DeliveryHistoryDTO deliveryHistoryDTO) throws Exception {
            deliveryHistoryService.createDeliveryHistoryAndDeleteCallHistory(deliveryHistoryDTO);
            log.info(deliveryHistoryDTO.getDeliveredUser() + " <- " + deliveryHistoryDTO.getCallerUser() + " : deliveryHistory is created.");
            return deliveryHistoryDTO;
    }
}
