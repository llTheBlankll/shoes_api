<?php

// Import API
require "api_calls.php";
require "dto/ProductSearch.php";

// Import DTOs
use dto\ProductSearch;

// Initialize Product API
$productAPI = new ProductAPI();

// Empty dictionary
$results = array();

if (!empty($_POST["submitSearch"])) {

	$search = new ProductSearch();
	$search->setPrice($_POST["budget"] ?? null);

	switch ($_POST["foot_width"]) {
		case "Narrow":
			$search->setSize(6);
			break;
		case "Medium":
			$search->setSize(8);
			break;
		case "Wide":
			$search->setSize(10);
			break;
		default:
			$search->setSize(40);
			break;
	}

	$search->setBrand($_POST["shoe_brand"] ?? array());
	$search->setColor($_POST["shoe_color"] ?? "");
	$search->setCategory($_POST["shoe_category"] ?? "");
	$search->setGender($_POST["shoe_gender"] ?? "");
	$search->setAvailability($_POST["shoe_availability"] ?? "");
	$results = json_decode($productAPI->filterProducts($search))->_embedded->productDTOes;
}


?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Basketball Shoe Survey</title>
</head>
<body>
<h1>Find Your Perfect Basketball Shoes</h1>

<form action="" method="POST">
    <!-- Question 1 -->
    <h3>1. What type of player are you?</h3>
    <p>Different playing styles call for different shoes.</p>
    <input type="radio" name="player_type" value="Quick and shifty guard"> Quick and shifty guard<br>
    <input type="radio" name="player_type" value="Versatile wing or forward"> Versatile wing or forward<br>
    <input type="radio" name="player_type" value="Big and powerful center"> Big and powerful center<br>

    <!-- Question 2 -->
    <h3>2. What type of basketball shoes do you like?</h3>
    <input type="radio" name="shoe_type" value="Low Top"> Low Top<br>
    <input type="radio" name="shoe_type" value="Mid Top"> Mid Top<br>
    <input type="radio" name="shoe_type" value="High Top"> High Top<br>

    <!-- Question 3 -->
    <h3>3. How wide are your feet?</h3>
    <p>This will make sure that the shoes fit your feet.</p>
    <input type="radio" name="foot_width" value="Narrow"> arrow<br>
    <input type="radio" name="foot_width" value="Regular">Regular<br>
    <input type="radio" name="foot_width" value="Wide">Wide<br>

    <!-- Question 4 -->
    <h3>4. Do you play outdoors a lot?</h3>
    <p>The rubber and upper material of outdoor shoes need to be more durable.</p>
    <input type="radio" name="play_outdoors" value="No, very little"> No, very little<br>
    <input type="radio" name="play_outdoors" value="All the time"> All the time<br>

    <!-- Question 5 -->
    <h3>5. What shoe brands do you like?</h3>
    <p>Select all shoe brands you like to play in.</p>
    <input type="checkbox" name="shoe_brand[]" value="Nike"> Nike<br>
    <input type="checkbox" name="shoe_brand[]" value="Adidas"> Adidas<br>
    <input type="checkbox" name="shoe_brand[]" value="Jordan"> Jordan<br>
    <input type="checkbox" name="shoe_brand[]" value="Puma"> Puma<br>
    <input type="checkbox" name="shoe_brand[]" value="Under Armour"> Under Armour<br>
    <input type="checkbox" name="shoe_brand[]" value="New Balance"> New Balance<br>
    <input type="checkbox" name="shoe_brand[]" value="Air Jordan"> Air Jordan<br>
    <input type="checkbox" name="shoe_brand[]" value="Li-Ning"> Li-Ning<br>

    <!-- Question 6 -->
    <h3>6. How much are you willing to spend?</h3>
    <p>Some shoes cost up to $200, but there are also great budget sneakers under $100.</p>
    <input type="number" name="budget" min="50" max="10000"> USD<br><br>

    <input type="submit" name="submitSearch" value="Submit">
</form>
<table>
    <tr>
        <th>Shoe</th>
        <th>Brand</th>
        <th>Price</th>
        <th>Size</th>
        <th>Color</th>
        <th>Category</th>
        <th>Gender</th>
        <th>Availability</th>
    </tr>

    <?php
    foreach ($results as $result) {
        echo "<tr>";
        echo "<td>" . $result->model . "</td>";
        echo "<td>" . $result->brand . "</td>";
        echo "<td>" . $result->price . "</td>";
        echo "<td>" . $result->size . "</td>";
        echo "<td>" . $result->color . "</td>";
        echo "<td>" . $result->category . "</td>";
        echo "<td>" . $result->gender . "</td>";
        echo "<td>" . $result->availability . "</td>";
        echo "</tr>";
    }
    ?>
</table>
</body>
</html>