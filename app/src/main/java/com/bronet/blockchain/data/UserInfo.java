package com.bronet.blockchain.data;

/**
 * Created by 18514 on 2019/7/6.
 */

public class UserInfo {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    private Result result;
    public class Result{
        private String id;
        private String userid;
        private String avatar;
        private String username;
        private int member_level;
        private String lockStatus;
        private String inviteCode;
        private String fromUserId;
        private String registerTime;
        private int isPartner;
        private int memberLevel;
        private int inviteUserCount;
        private int teamUserCount;
        private int starLevel;
        private String nceLevel;

        public int getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(int starLevel) {
            this.starLevel = starLevel;
        }

        public int getTeamUserCount() {
            return teamUserCount;
        }

        public void setTeamUserCount(int teamUserCount) {
            this.teamUserCount = teamUserCount;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        private String nid;
        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getMember_level() {
            return member_level;
        }

        public void setMember_level(int member_level) {
            this.member_level = member_level;
        }

        public String getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(String lockStatus) {
            this.lockStatus = lockStatus;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public int getIsPartner() {
            return isPartner;
        }

        public void setIsPartner(int isPartner) {
            this.isPartner = isPartner;
        }

        public int getMemberLevel() {
            return memberLevel;
        }

        public void setMemberLevel(int memberLevel) {
            this.memberLevel = memberLevel;
        }

        public String getMemberLevelLimit() {
            return memberLevelLimit;
        }

        public void setMemberLevelLimit(String memberLevelLimit) {
            this.memberLevelLimit = memberLevelLimit;
        }

        public int getInviteUserCount() {
            return inviteUserCount;
        }

        public void setInviteUserCount(int inviteUserCount) {
            this.inviteUserCount = inviteUserCount;
        }

        private String memberLevelLimit;

        public String getNceLevel() {
            return nceLevel;
        }

        public void setNceLevel(String nceLevel) {
            this.nceLevel = nceLevel;
        }
    }
}
