package com.technobel.restapiroomequipment.services;

import com.technobel.restapiroomequipment.models.dto.PersonDTO;
import com.technobel.restapiroomequipment.models.forms.LoginForm;
import com.technobel.restapiroomequipment.models.forms.RegisterUserForm;

public interface AuthService {

    PersonDTO register(RegisterUserForm form);

    PersonDTO login(LoginForm form);
}
