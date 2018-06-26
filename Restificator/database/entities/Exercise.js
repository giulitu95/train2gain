module.exports = class Exercise{
    constructor(id, name, description, muscleGroupId, imageUrl){
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroupId = muscleGroupId;
        this.imageUrl = imageUrl;
    }
}