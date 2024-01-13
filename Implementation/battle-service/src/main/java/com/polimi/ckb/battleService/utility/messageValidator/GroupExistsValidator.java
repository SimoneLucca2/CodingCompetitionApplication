package com.polimi.ckb.battleService.utility.messageValidator;

import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.GroupExists;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class GroupExistsValidator implements ConstraintValidator<GroupExists, Long> {
    private final GroupRepository groupRepository;

    @Override
    public boolean isValid(Long groupId, ConstraintValidatorContext context) {
        return groupRepository.existsById(groupId);
    }
}
