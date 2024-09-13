package com.cs309.tutorial.tests;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;



@RestController
public class TestController {


	//idea: have a key be a password, and have an array with account information
	//to display as well
	HashMap<String, Integer> login = new HashMap<String, Integer>();
	Random rand = new Random();
	private int password;
	//@GetMapping("/people/{firstName}")
	//public @ResponseBody Person getPerson(@PathVariable String firstName) {
	//Person p = peopleList.get(firstName);
	//return p;
	//}

	@GetMapping("/getTest")
	public String getTest(@RequestParam("username") String message) {
		return String.format("Hello, %s! You sent a get request with a parameter!", message);
	}
	
	@PostMapping("/postTest1")
	public String postTest1(@RequestParam(value = "username", defaultValue = "World") String message) {
		login.put(message, rand.nextInt(1000000000));
		return String.format("Hello, %s! You created an account with password: " + login.get(message) , message);
	}
	
	@PostMapping("/postTest2")
	public String postTest2(@RequestBody final Object testData) {

		System.out.println(testData);
		return String.format("Hello " + testData.toString() + ", ! You sent a post request with a requestbody!");
	}
	
	@DeleteMapping("/deleteTest")
	public String deleteTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		login.remove(message);
		return String.format(message + " removed successfully");
	}
	
	@PutMapping("/putTest")
	public String putTest(@RequestParam(value = "username", defaultValue = "World") String message) {
		login.put(message, rand.nextInt(1000000000));
		return String.format(message + " put successfully");
	}

	@GetMapping("/listTest")
	public String listTest() {
		return login.toString();
	}


}
