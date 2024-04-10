package com.example.foodcorner.Models

class Post {
    var postUrl:String=""
    var ingredients:String=""
    var uid: String=""
    var time: String=""
    var isLiked: Boolean = false
    constructor()
    constructor(postUrl: String, ingredients: String) {
        this.postUrl = postUrl
        this.ingredients = ingredients
    }

    constructor(postUrl: String, ingredients: String, uid: String, time: String) {
        this.postUrl = postUrl
        this.ingredients = ingredients
        this.uid = uid
        this.time = time
    }
}