<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Form</title>
</head>
<body>
 <form action="api/registerUser" method="POST">
  <input value="123" type="text" id="deviceId" name="deviceId"><br>
  <input onclick="sendJSON()">
</form>
</body>
<script>
/*  	let jsonObj = {
		    deviceId: "123"
		};

	let url = "http://localhost:8080/qutarpuzzle/fetchScoreboard";
	let headers = {
	    'Accept': 'application/json',
	    'Content-Type': 'application/json'
	};  */
	
/*  	let jsonObj = {
		    deviceId: "123",
		    timeFinished: 1040,
		};
	
	let url = "http://localhost:8080/qutarpuzzle/storeScore";
	let headers = {
	    'Accept': 'application/json',
	    'Content-Type': 'application/json'
	};  */
	
/*  	let jsonObj = {
		    deviceId: "1234",
		    displayName: "tester2",
		};
	
	let url = "http://localhost:8080/qutarpuzzle/addUser";
	let headers = {
	    'Accept': 'application/json',
	    'Content-Type': 'application/json'
	}; */
	
 	let jsonObj = {
		    deviceId: "123",
		    trophyPosX: 342.23,
		    trophyPosY: 44.523,
		    trophyPosZ: 23,
		    trophyType: 1
		};
	
	let url = "http://localhost:8080/qutarpuzzle/addTrophy";
	let headers = {
	    'Accept': 'application/json',
	    'Content-Type': 'application/json'
	};
	
	async function makePostRequest(url, requestType, headers, fnc) {
		
		switch (fnc)
		{
		case: 
		}
		
	    await fetch(
	        url,
	        {
	            method: requestType,
	            headers: headers,
	            body: JSON.stringify(jsonObj)
	        },
	    ).then(async rawResponse =>{
	        var content = await rawResponse.json()
	        console.log(content);
	    });
	}
	
	makePostRequest(url, "POST", headers);
	
	</script>
</html>