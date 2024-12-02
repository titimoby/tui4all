package main

import (
	"fmt"
	"os"
	"tui4all/go/steam/api"
	"tui4all/go/steam/tui"
)

func main() {
	// get the OS variable STEAM_API_KEY
	apiKey := os.Getenv("STEAM_API_KEY")
	if apiKey == "" {
		fmt.Println("STEAM_API_KEY not set")
		os.Exit(1)
	}

	// get the OS variable STEAM_ID
	steamID := os.Getenv("STEAM_ID")
	if steamID == "" {
		fmt.Println("STEAM_ID not set")
		os.Exit(1)
	}

	recentlyPlayedGames := api.GetRecentlyPlayedGames(apiKey, steamID)

	tui.StartTui(recentlyPlayedGames)
}
