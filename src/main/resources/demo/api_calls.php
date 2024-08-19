<?php

use dto\ProductSearch;

class ProductAPI {

	private $ch;
	private $apiURL = "http://localhost:8080/api/v1/products/search";

	public function __construct()
	{
		$this->ch = curl_init();
	}

	public function filterProducts(ProductSearch $search) {
		// Set Host
		curl_setopt($this->ch, CURLOPT_URL, $this->apiURL);
		// POST Method
		curl_setopt($this->ch, CURLOPT_POST, 1);
		// Add JSON string to POST fields
		curl_setopt($this->ch, CURLOPT_POSTFIELDS, $search->toJSON());
		// Set the content type to application/json
		curl_setopt($this->ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
		// Return response instead of outputting
		curl_setopt($this->ch, CURLOPT_RETURNTRANSFER, true);
		// Execute the request
		return curl_exec($this->ch);
	}
}