<?php

namespace dto;

class Feature
{
	private int $id;
	private string $name;

	/**
	 * @param int $id
	 * @param string $name
	 */
	public function __construct(int $id, string $name)
	{
		$this->id = $id;
		$this->name = $name;
	}


	public function getId(): int
	{
		return $this->id;
	}

	public function setId(int $id): Feature
	{
		$this->id = $id;
		return $this;
	}

	public function getName(): string
	{
		return $this->name;
	}

	public function setName(string $name): Feature
	{
		$this->name = $name;
		return $this;
	}
}