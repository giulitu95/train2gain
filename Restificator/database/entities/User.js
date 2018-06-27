module.exports = class User{
    constructor(id, email, password, name, lastName, userType, registrationDate){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.userType = userType;
        this.registrationDate = registrationDate;
    }
}