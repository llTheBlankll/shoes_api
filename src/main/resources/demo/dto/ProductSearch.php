<?php

namespace dto;

use Cassandra\Date;

class ProductSearch
{
	private ?array $brand;
	private ?string $model;
	private ?string $category;
	private ?string $color;
	private ?int $size;
	private ?string $gender;
	private ?string $availability;
	private ?float $price;
	private ?string $description;
	private ?int $stock;
	private ?Date $releaseDate;
	/**
	 * @var Feature[]
	 */
	private array $features;

	public function getBrand(): array
	{
		return $this->brand;
	}

	public function setBrand(?array $brand): ProductSearch
	{
		$this->brand = $brand;
		return $this;
	}

	public function getModel(): string
	{
		return $this->model;
	}

	public function setModel(?string $model): ProductSearch
	{
		$this->model = $model;
		return $this;
	}

	public function getCategory(): string
	{
		return $this->category;
	}

	public function setCategory(?string $category): ProductSearch
	{
		$this->category = $category;
		return $this;
	}

	public function getColor(): string
	{
		return $this->color;
	}

	public function setColor(?string $color): ProductSearch
	{
		$this->color = $color;
		return $this;
	}

	public function getSize(): int
	{
		return $this->size;
	}

	public function setSize(?int $size): ProductSearch
	{
		$this->size = $size;
		return $this;
	}

	public function getGender(): string
	{
		return $this->gender;
	}

	public function setGender(?string $gender): ProductSearch
	{
		$this->gender = $gender;
		return $this;
	}

	public function getAvailability(): string
	{
		return $this->availability;
	}

	public function setAvailability(?string $availability): ProductSearch
	{
		$this->availability = $availability;
		return $this;
	}

	public function getPrice(): float
	{
		return $this->price;
	}

	public function setPrice(?float $price): ProductSearch
	{
		$this->price = $price;
		return $this;
	}

	public function getDescription(): string
	{
		return $this->description;
	}

	public function setDescription(?string $description): ProductSearch
	{
		$this->description = $description;
		return $this;
	}

	public function getStock(): int
	{
		return $this->stock;
	}

	public function setStock(?int $stock): ProductSearch
	{
		$this->stock = $stock;
		return $this;
	}

	public function getReleaseDate(): Date
	{
		return $this->releaseDate;
	}

	public function setReleaseDate(?Date $releaseDate): ProductSearch
	{
		$this->releaseDate = $releaseDate;
		return $this;
	}

	public function getFeatures(): array
	{
		return $this->features;
	}

	public function setFeatures(?array $features): ProductSearch
	{
		$this->features = $features;
		return $this;
	}

	public function toArray(): array
	{
		return [
			'price' => $this->getPrice(),
			'size' => $this->getSize(),
			'brand' => $this->getBrand(),
			'color' => $this->getColor(),
			'category' => $this->getCategory(),
			'gender' => $this->getGender(),
			'availability' => $this->getAvailability(),
		];
	}

	public function __toString(): string
	{
		return json_encode($this->toArray());
	}

	public function toJSON() {
		return json_encode($this->toArray());
	}
}