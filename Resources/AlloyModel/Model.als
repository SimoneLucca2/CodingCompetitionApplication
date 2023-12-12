//SIGNATURES
//Definitions of useful signatures
sig Nickname{}
sig Title{}
sig ProgrammingLanguage{}

//Definitions of deadline statuses
abstract sig DeadlineStatus{}
one sig Expired extends DeadlineStatus{}
one sig Unexpired extends DeadlineStatus{}

//Used to represent a tuple <date, time>
sig DateTime{
    date: one Int,
    time: one Int
}{
    time >= 0
    date >= 1
}

//Definitions of tournament statuses
abstract sig TournamentStatus{}
one sig Ongoing extends TournamentStatus{}
one sig Close extends TournamentStatus{}

//Definition of battles and tournaments that have involved a user
abstract sig History{}
one sig StudentHistory{
    var battlesAsStudent: set Student -> Battle -> BattleScore,
    var tournamentsAsStudent: set Student -> Tournament -> TournamentScore,
    var achievedBadges: set Student -> GamificationBadge
}
one sig EducatorHistory{
    var battlesAsEducator: set Educator -> Battle,
    var tournamentsAsEducator: set Educator -> Tournament,
    var createdBadges: set Educator -> GamificationBadge
}

//Type of users (Student, Educator)
abstract sig User{}
sig Student extends User{
    userName: one Nickname
}
sig Educator extends User{
    userName: one Nickname
}

//Definition of tournament
sig Tournament{
    name: one Title,
    creator: one Educator,
    registrationDeadline: one DateTime,
    var registrationDeadlineStatus: one DeadlineStatus,
    chosenBadge: set GamificationBadge,
    var status: one TournamentStatus
}

//Definition of battle
sig Battle{
    name: one Title,
    //tournament: one Tournament,
    creator: one Educator,
    gitRepoLink: one Repository,
    registrationDeadline: one DateTime,
    submissionDeadline: one DateTime,
    var registrationDeadlineStatus: one DeadlineStatus,
    var submissionDeadlineStatus: one DeadlineStatus,
    language: one ProgrammingLanguage
}{
    registrationDeadline.date < submissionDeadline.date or
    (registrationDeadline.date = submissionDeadline.date and registrationDeadline.time < submissionDeadline.time)
}

//Definition of boolean predicate for gamification badges
sig BooleanPredicate{}

//Definition of gamification badge
sig GamificationBadge{
    name: one Title,
    predicates: some BooleanPredicate,
    creator: one Educator
}

//Definition of GitHub repository
sig Repository{}

//Definition of battle's score
sig BattleScore{
    score: one Int
}{
    score >= 0
}

//Definition of tournament's score
sig TournamentScore{
    score: one Int
}{
    score >= 0
}

//Assosiaction between tournaments and their battles
sig BattlesTournamentsAssociation{
    link: Tournament -> set Battle
}


//FUNCTIONS
//Get tournaments done by the given student
fun getTournamentsByStudent[s: Student]: set Tournament{
    s.(StudentHistory.tournamentsAsStudent.TournamentScore)
}

//Get battles done by the given student
fun getBattlesByStudent[s: Student]: set Battle{
    s.(StudentHistory.battlesAsStudent.BattleScore)
}

//Get tournaments that involved the given educator
fun getTournamentsByEducator[e: Educator]: set Tournament{
    e.(EducatorHistory.tournamentsAsEducator)
}

//Get battles created by the given educator         NOT USED 
fun getBattlesByEducator[e: Educator]: set Battle{
    e.(EducatorHistory.battlesAsEducator)
}

//Get badges achieved by the given student
fun getAchievedBadgesByStudent[s: set Student]: set GamificationBadge{
    s.(StudentHistory.achievedBadges)
}

//Get badges created by the given educator
fun getCreatedBadgesByEducator[e: Educator]: set GamificationBadge{
    e.(EducatorHistory.createdBadges)
}

//Get battles in the given tournament
fun getBattlesByTournament[t: Tournament]: set Battle{
    t.(BattlesTournamentsAssociation.link)
}

//Get tournament of the given battle
fun getTournamentByBattle[b: Battle]: one Tournament{
    BattlesTournamentsAssociation.link.b
}

//Get the score of the given student in the given tournament
fun getScoreByStudentAndTournament[s: Student, t: Tournament]: set TournamentScore{
    t.(s.(StudentHistory.tournamentsAsStudent))
}

//Get scores by student and set of battles
fun getScoreByBattleAndStudent[s: Student, b: set Battle]: set BattleScore{
    b.(s.(StudentHistory.battlesAsStudent))
}

//Get scores by the given battle
fun getScoresByBattle[b: Battle]: set BattleScore{
    b.(Student.(StudentHistory.battlesAsStudent))
}


//FACTS
//Every battle has its own repository
fact differentReposForDifferentBattles {
    all disj b1, b2: Battle |
             b1.gitRepoLink != b2.gitRepoLink
}

//Students registered to a battle are registered also to the tournament in which the battle is held
fact studentsInBattleAreInItsTournament {
    all s: Student |
        all b: Battle | b in s.getBattlesByStudent implies
            b.getTournamentByBattle in s.getTournamentsByStudent
}

//Every user has a unique nickname
fact noStudentAndEducatorWithSameUsername {
    all s: Student |
        all e: Educator |
            s.userName != e.userName
}
fact noStudentsWithSameUsername {
    all disj s1, s2: Student |
             s1.userName != s2.userName
}
fact noEducatorsWithSameUsername {
    all disj e1, e2: Educator |
             e1.userName != e2.userName
}

//Tournament name is unique with respect to those tournaments whose status is Open
fact noOpenTournamentWithTheSameName {
    all disj t1, t2: Tournament |
             t1.name = t2.name implies not (t1.status = Ongoing and t2.status = Ongoing)
}

//Every battle within a tournament has a unique name
fact noBattleWithTheSameNameInTheSameTournament{
    all disj b1, b2: Battle |
             b1.getTournamentByBattle = b2.getTournamentByBattle implies b1.name != b2.name
}

//The educator who creates a battle is the creator of its tournament or has been given permission by its creator
fact battleCreatorIsInvolvedInTheTournament{
    all b: Battle |
        b.getTournamentByBattle in b.creator.getTournamentsByEducator
}

//No student can register to a battle if its registration deadline has expired
fact noRegistrationToBattleIfDeadlineExpired{
    all b: Battle |
        all s: Student |
            always b.registrationDeadlineStatus = Expired and b not in s.getBattlesByStudent implies
                always b not in s.getBattlesByStudent
}

//No student can register to a tournament if its registration deadline has expired
fact noRegistrationToTournamentIfDeadlineHexpired{
    all t: Tournament |
        all s: Student |
            always t.registrationDeadlineStatus = Expired and t not in s.getTournamentsByStudent implies
                always t not in s.getTournamentsByStudent
}

//DeadlineStatus = Expired only after DeadlineStatus = Unexpired
/*fact tournamentRegistrationDeadlineExpiredAfterItWasUnexpired{
    all t: Tournament |
        t.registrationDeadlineStatus = Expired implies
            before t.registrationDeadlineStatus = Unexpired
}
fact battleRegistrationDeadlineExpiredAfterItWasUnexpired{
    all b: Battle |
        b.registrationDeadlineStatus = Expired implies
            before b.registrationDeadlineStatus = Unexpired
}
fact battleSubmissionDeadlineExpiredAfterItWasUnexpired{
    all b: Battle |
        b.submissionDeadlineStatus = Expired implies
            before b.submissionDeadlineStatus = Unexpired
}*/

//When the deadline has expired, it remains expired forever
fact onceTournamentDeadlineExpiredItRemainsExpiredForever{
    all t: Tournament |
        always t.registrationDeadlineStatus = Expired implies
            after always t.registrationDeadlineStatus = Expired
}
fact onceBattleRegistrationDeadlineExpiredItRemainExpiredForever{
    all b: Battle |
        always b.registrationDeadlineStatus = Expired implies
            after always b.registrationDeadlineStatus = Expired
}
fact onceBattleSubmissionDeadlineExpiredItRemainsExpiredForever{
    all b: Battle |
        always b.submissionDeadlineStatus = Expired implies
            after always b.submissionDeadlineStatus = Expired
}

//Gamification badges in a tournament has been generated by the tournament creator
fact tournamentBadgesGeneratedByTournamentCreator{
    all t: Tournament |
        all gb: GamificationBadge |
            gb  in t.chosenBadge implies gb in t.creator.getCreatedBadgesByEducator
}

//No overlapping battles in the same tournament
fact noOverlappingBattlesInTheSameTournament{
    all disj b1, b2: Battle |
        b1.getTournamentByBattle = b2.getTournamentByBattle implies
            ((b1.submissionDeadline.date < b2.registrationDeadline.date) or
                (b1.submissionDeadline.date = b2.registrationDeadline.date and
                    b1.submissionDeadline.time <= b2.registrationDeadline.time))
}

//Once a badge is achieved, it cannot be removed from the student's history
fact achievedBadgeesRemainForever{
    all s: Student |
        all gb: GamificationBadge |
            always gb in s.getAchievedBadgesByStudent implies
                after always gb in s.getAchievedBadgesByStudent
}

//Once a tournament has been closed, its states remains close forever
fact onceTournamentClosedItRemainsClosed{
    all t: Tournament |
        always t.status = Close implies
            after always t.status = Close
}

//The score gotten at the end of a tournament is the sum of all scores gotten in the battles within the tournament itself
fact sumBattlesScoresEqualToTournamentScore{
    all s: Student |
        all t: Tournament |
            getScoreByStudentAndTournament[s, t].score = sum[getScoreByBattleAndStudent[s, getBattlesByTournament[t]].score]           
}

//All battles must be within a tournament
fact allBattlesAreWithinATournament{
    all b: Battle |
        b.getTournamentByBattle != none
}

//If a tournament is closed, its registration deadline has expired
fact registrationDeadlineExpiredIfTournamentClose{
    all t: Tournament |
        t.status = Close implies t.registrationDeadlineStatus = Expired 
}

//Succession of TournamentStatus
fact successionOfTournamentStatus{
    all t: Tournament |
        t.status = Close implies 
            before t.status = Ongoing
}

//If the registration deadline of a tournament has not expired yet, no buttle can be created within it
fact noBattleIfTournamentRegistrationDeadlineHasNotExpired{
    all t: Tournament |
        t.registrationDeadlineStatus = Unexpired implies no t.getBattlesByTournament
            //t.getBattlesByTournament = none
}

//If a battle is not concluded yet, no scores can be definitively assign w.r.t it
fact noScoreIfBattleNotConluded{
    all b: Battle |
        (b.registrationDeadlineStatus = Unexpired or b.submissionDeadlineStatus = Unexpired) implies
            no b.getScoresByBattle
}


//PREDICATES
pred show {
    #Battle = 3
    #StudentHistory.battlesAsStudent = 1
    #Student = 2
    #GamificationBadge = 2
    #Tournament = 2
}

run show
