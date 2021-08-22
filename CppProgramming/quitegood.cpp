/**
 * Computer Programming 2 (COMP2711, COMP8801)
 * Practical 1: Quite Good Numbers
 * A  _quite good_  number is an integer whose  _badness_—the size of the difference between the sum of its divisors and
 * the number itself—is not greater than a specified value.
 * For example, if the maximum badness is set at 3, there are 12 quite good numbers less than 100: 2, 3, 4, 6, 8, 10, 16, 18, 20, 28, 32, and 64.
 */
#include <iostream>
#include <cstdlib>
using namespace std;

int main(int argc, char *argv[])
{

    int limit, badness;

    if (argc == 3) {
        limit = atoi(argv[1]);
        badness = atoi(argv[2]);
    }

    int count = 0;
    if (badness >=0){
        for (int candidate = 2; candidate < limit; candidate++) {
            int total = 1;
            for (int factor = 2; factor*factor <= candidate; factor++) {
                if (candidate % factor == 0) {
                    if (factor*factor != candidate){
                        total = total + factor + candidate/factor;
                    } else{
                        total += factor;
                    }

                }
            }
            if (abs(total-candidate) <= badness) {
                cout << candidate << " ";
                count++;
            }
        }
    }else{
        for (int candidate = 2; candidate < limit; candidate++) {
            int total = 1;
            for (int factor = 2; factor*factor <= candidate; factor++) {
                if (candidate % factor == 0) {
                    if (factor*factor != candidate){
                        total = total + factor + candidate/factor;
                    } else{
                        total += factor;
                    }

                }
            }
            if (abs(total-candidate) <= abs(badness)) {
                count++;
            }
        }
        cout << count << endl;
    }




}