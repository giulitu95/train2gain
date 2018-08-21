module.exports = class AbstractResponse{
    constructor(localId){
        this.localId = localId;
        this.id = null;
    }
    addRemoteId(remoteId){
        this.id = remoteId;
    }
}