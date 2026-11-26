# Bintang Bank ATM System

An interactive Java Swing desktop ATM simulation built in Apache NetBeans. Designed with a dark-and-gold terminal theme, on-screen tactile keypads, real-time session security, and automated banknote breakdown calculations.

## Key Features

- **Interactive Card Insertion & Live Clock (`HOME`):** Real-time system status indicator, live digital clock, and simulated chip-reading transition.
- **Tactile PIN Verification (`Next`):** On-screen numeric keypad (`0–9`, `CLEAR`, `ENTER`) with masked PIN entry, audio beep feedback, and a **3-attempt security lockout** that retains the card after repeated invalid tries.
- **Main Transaction Dashboard (`Menu`):**
  - **60-Second Auto-Eject Timer:** Automatically ends the session and ejects the card on inactivity.
  - **Fast Cash (RM 100):** One-tap instant cash withdrawal.
  - **Mini Statement:** Tracks and displays all deposits, withdrawals, and fast-cash transactions with timestamps during the active session.
- **Cash Withdrawal (`WITHDRAW`):** Fast-cash presets (`RM 100` – `RM 1,500`) plus a built-in custom amount keypad. Validates multiples of `RM 10`, prevents overdrafts, and calculates exact **Malaysian Ringgit banknote denominations** (`RM 100`, `RM 50`, `RM 20`, `RM 10`).
- **Cash Deposit (`DEPOSIT`):** Preset deposit buttons and custom keypad input with note-counting simulation and confirmation prompts.
- **Balance Inquiry & Receipts (`BALANCE`):** Formatted currency display (`RM #,##0.00`), a **Show/Hide privacy toggle**, and printable digital ATM receipt pop-ups across transactions.

## 🔑 Demo Credentials

Use the following default details to test the ATM terminal:
- **Account Number:** `8801-4492-1029` *(auto-filled on login)*
- **PIN Code:** `1234`
- **Starting Balance:** `RM 1,000.00`

## 🛠️ Tech Stack

- **Language:** Java (JDK 8+)
- **GUI Framework:** Java Swing (`JFrame`, `GridBagLayout`, `BorderLayout`, `Timer`)
- **IDE:** Apache NetBeans (Ant Build)
