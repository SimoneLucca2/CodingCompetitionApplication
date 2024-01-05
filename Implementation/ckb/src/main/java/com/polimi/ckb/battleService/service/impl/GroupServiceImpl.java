package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinsGroupDto;
import com.polimi.ckb.battleService.dto.StudentLeavesGroupDto;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final BattleRepository battleRepository;
    private final StudentRepository studentRepository;


    @Override
    @Transactional
    public StudentGroup inviteStudentToGroup(@Valid StudentInvitesToGroupDto studentInvitesToGroupDto) {
        return null;
    }

    @Override
    @Transactional
    public StudentGroup joinGroup(@Valid StudentJoinsGroupDto studentJoinsGroupDto) {
        return null;
    }

    @Override
    @Transactional
    public StudentGroup leaveGroup(@Valid StudentLeavesGroupDto studentLeavesGroupDto) {
        return null;
    }
}
