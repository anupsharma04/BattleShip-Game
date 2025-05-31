# 🚢 Battleship Game (Java)

A 2-player, text-based **Battleship game** written in Java — developed as part of a programming assignment.

### 📅 Date: May 27, 2025  
### 👨‍💻 Author: Anup Sharma

---

## 📌 Overview

This Java program simulates the classic Battleship board game in a simplified, console-based format. It features:

- A 5x5 game grid for each player  
- Manual ship placement with coordinate input  
- Turn-based gameplay with hit/miss tracking  
- Real-time feedback and win detection  
- Final board reveal after the game concludes  
- Only **hits and misses** are shown — ship locations remain hidden during play

---

## 🎓 Assignment Context

> This project was developed as part of a **Module 4 Homework Assignment** for an introductory programming course (2024–2025).  
> The goal was to demonstrate mastery of Java arrays, loops, conditionals, input handling, and modular design by building an interactive 2-player game.

---

## 🎮 How It Works

### 1. Setup Phase
- Each player is prompted to place **5 ships** on a 5x5 grid using row/column input.
- Invalid or duplicate placements are rejected and must be re-entered.

### 2. Battle Phase
- Players take turns firing at each other’s boards.
- A hit is marked with `X`, a miss with `O`.
- Hits are only shown on the tracking board — players never see the opponent's full board.

### 3. Game End
- The game ends instantly when one player’s ships are all destroyed.
- The winner is announced.
- Final boards for both players are displayed, including all ship positions.

---

## 🧩 Key Features

- 🧠 **Robust input validation**: no crashing on bad input or invalid moves
- ⚔️ **Turn-based game logic** with real-time hit/miss detection
- 🧱 **Modular structure** with clean helper methods:
  - `resetBoard()` – initialize board cells to default
  - `copyBoard()` – transfer ship placements
  - `printBoard()` – display a formatted game board
  - `attackAndPrint()` – handle hit/miss logic during gameplay
  - `getValidatedInt()` – ensures only valid integers are accepted for input
  - `isValidCoord()` – checks if a coordinate is within the game board limits

- ✅ 100% console-based, no external libraries needed
- 🎯 Handles invalid or duplicate actions without crashing

---

## 💻 Run Instructions

1. **Clone or download** this repository:
   ```bash
   git clone https://github.com/anupsharma04/battleship-java.git
   cd battleship-java
