package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.SaveRepositoryLinkDto;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinsGroupDto;
import com.polimi.ckb.battleService.dto.StudentLeavesGroupDto;
import com.polimi.ckb.battleService.entity.StudentGroup;

public interface GroupService {
    void inviteStudentToGroup(StudentInvitesToGroupDto studentInvitesToGroupDto);
    StudentGroup joinGroup(StudentJoinsGroupDto studentJoinsGroupDto);
    StudentGroup leaveGroup(StudentLeavesGroupDto studentLeavesGroupDto);
    Object getAllGroupsRepoLinksByBattle(String battleId);

    void saveRepositoryUrl(SaveRepositoryLinkDto saveRepositoryLinkDto);
}
