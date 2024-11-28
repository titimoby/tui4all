package api

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os"
)

func GetRecentlyPlayedGames(apiKey string, steamID string) ResponseRecentlyPlayedGames {
	// construct the URL
	url := fmt.Sprintf("http://api.steampowered.com/IPlayerService/GetRecentlyPlayedGames/v0001/?key=%s&steamid=%s&format=json", apiKey, steamID)

	// make the HTTP GET request
	resp, err := http.Get(url)
	if err != nil {
		fmt.Println("Error making HTTP request:", err)
		os.Exit(1)
	}
	defer resp.Body.Close()

	// read the response body
	body, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body:", err)
		os.Exit(1)
	}

	// unmarshal the JSON response into the struct
	var recentlyPlayedGames ResponseRecentlyPlayedGames
	err = json.Unmarshal(body, &recentlyPlayedGames)
	if err != nil {
		fmt.Println("Error unmarshalling JSON response:", err)
		os.Exit(1)
	}

	return recentlyPlayedGames
}
