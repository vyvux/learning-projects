/**
 * Computer Programming 2 (COMP2711, COMP8801)
 * Practical 2: Roman Numbers
 *
 * Convert Roman number to Arabic number, and vice versa.
 * If the input is invalid, convert the possible valid prefix of the string. If no valid prefix, return 0.
 *
 *  |   | Thousands | Hundreds | Tens | Units |
    |---|-----------|----------|------|-------|
    | 1 | M         | C        | X    | I     |
    | 2 | MM        | CC       | XX   | II    |
    | 3 | MMM       | CCC      | XXX  | III   |
    | 4 |           | CD       | XL   | IV    |
    | 5 |           | D        | L    | V     |
    | 6 |           | DC       | LX   | VI    |
    | 7 |           | DCC      | LXX  | VII   |
    | 8 |           | DCCC     | LXXX | VIII  |
    | 9 |           | CM       | XC   | IX    |
 */
#include <iostream>
#include <cctype>

using namespace std;

int main(int argc, char *argv[])
{
    string units[10] = {"","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    string tens[10] = {"","X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    string hundreds[10] = {"","C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    string thousands[4] = {"","M", "MM", "MMM"};

    string roman;
    getline (cin, roman);

    while (!roman.empty()){
        int position = 0;
        for (char i : roman){ // loop of input char
           if (isdigit(i)){
               position++;
           }
           else break;
        }
        // input is Arabic number
        if (position > 0){
            int number = stoi(roman.substr(0,position));

            int thousand = number/1000;
            if (thousand > 0) number %= 1000;

            int hundred = number/100;
            if( hundred > 0) number %= 100;

            int ten = number/10;
            if (ten > 0) number %= 10;

            cout << thousands[thousand] << hundreds[hundred] << tens[ten] << units[number]<< endl;
        }
        else // input is Roman number
        {
            // to uppercase
            for (int i = 0; i < roman.length(); i++){
                roman[i] = toupper(roman[i]);
            }

            // convert
            int sum = 0;

            if (roman[0] == 'M'){
                for (int i = 3; i >= 1; i--) {
                    if (roman.substr(0,min(roman.length(), thousands[i].length())) == (thousands[i])){
                        sum += i * 1000;
                        roman = roman.substr(thousands[i].length());
                    }
                }
            }
            if (roman[0] == 'C' || roman[0] == 'D'){
                for (int i = 9; i >= 1; i--) {
                    if (roman.substr(0, min(roman.length(), hundreds[i].length())) == (hundreds[i])){
                        sum += i * 100;
                        roman = roman.substr(hundreds[i].length());
                    }

                }
            }
            if (roman[0] == 'X' || roman[0] == 'L'){
                for (int i = 9; i >= 1; i--) {
                    if (roman.substr(0, min(roman.length(), tens[i].length())) == (tens[i])){
                        sum += i * 10;
                        roman = roman.substr(tens[i].length());
                    }

                }
            }
            if (roman[0] == 'I' || roman[0] == 'V'){
                for (int i = 9; i >= 1; i--) {
                    if (roman.substr(0,min(roman.length(), units[i].length())) == (units[i])) {
                        sum += i ;
                        roman = roman.substr(units[i].length());
                    }
                }
            }

            cout << sum << endl;
        }

        getline (cin, roman);
    }

    return 0;
}

