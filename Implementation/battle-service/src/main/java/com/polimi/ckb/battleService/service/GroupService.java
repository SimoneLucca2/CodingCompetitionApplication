package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;

import java.util.List;

public interface GroupService {
    void inviteStudentToGroup(StudentInvitesToGroupDto studentInvitesToGroupDto);
    StudentGroup joinGroup(StudentJoinsGroupDto studentJoinsGroupDto);
    StudentGroup leaveGroup(StudentLeavesGroupDto studentLeavesGroupDto);
    List<StudentGroup> getAllGroupsRepoLinksByBattle(Long battleId);

    StudentGroup saveRepositoryUrl(SaveGroupRepositoryLinkDto saveGroupRepositoryLinkDto);

    StudentGroup manuallyEvaluateGroup(Long groupId, float score, Long battleId);
    List<Student> getStudentsInBattle(Long battleId, Long groupId);
}
