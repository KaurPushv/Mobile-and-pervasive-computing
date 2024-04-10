package com.example.foodcorner.Models

class Reel {
    var reelUrl:String=""
    var ingredients:String=""
    var profileLink: String?=null
    constructor()
    constructor(reelUrl: String, ingredients: String) {
        this.reelUrl = reelUrl
        this.ingredients = ingredients
    }

    constructor(reelUrl: String, ingredients: String, profileLink: String) {
        this.reelUrl = reelUrl
        this.ingredients = ingredients
        this.profileLink = profileLink
    }
}