package com.example.bodyboost


class Users {
    var id: Int = 0
    lateinit var account: String
    lateinit var password:String
    lateinit var email:String
    lateinit var created_type:String
    lateinit var status:String

    fun Users(account: String, password: String,email:String) {
        this.id = 0
        this.account = account
        this.password = password
        this.email = email
    }
    fun setUser_account(account: String) {
        this.account = account
    }

    fun setUser_password(password: String) {
        this.password = password
    }
    fun setUser_email(email: String){
        this.email = email
    }

    fun setcreated_type(created_type:String){
        this.created_type = created_type
    }

    fun setstatus(status:String){
        this.status = status
    }
    fun getUser_id(): Int {
        return id
    }

    fun getUser_account(): String {
        return account
    }

    fun getUser_password(): String {
        return password
    }

}