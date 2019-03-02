package com.haze420.android.model.apimodel

data class ProfileRequest(val first_name: String,
                          val last_name: String,
                          val email: String,
                          val phone: String,
                          val birthday: String) {
}


/*
{
	"first_name": "Test Name",
	"last_name": "Test last name",
	"email": "testaccount3@gmail.com",
	"birthday": "19/10/1986",
	"phone": "+37498253510"
}
 */