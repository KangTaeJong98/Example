#include <stdio.h>      // printf, scanf 함수를 사용하기 위한 헤더 파일
#include <conio.h>      // _getch, _kbhit 함수를 사용하기 위한 헤더 파일
#include <stdlib.h>     // rand, srand 함수를 사용하기 위한 헤더 파일
#include <stdbool.h>    // gcc에서 bool타입을 사용하기 위한 헤더 파일
#include <time.h>       // time 함수를 사용하기 위한 헤더 파일
#include <windows.h>    // 콘솔관련 함수를 사용하기 위한 헤더 파일
#include <math.h>       // abs 함수를 사용하기 위한 헤더 파일

// 매크로 상수를 사용하여 코드의 가독성을 높일 수 있다.
#define BOARD_WIDTH 30
#define BOARD_HEIGHT 20

// 방향키의 아스키 코드
// 이 값을 그대로 방향으로 사용
#define UP 72
#define DOWN 80
#define LEFT 75
#define RIGHT 77

// board에 저장되는 상태
#define EMPTY 0
#define WALL 1
#define COIN 2
#define HOLE 3

#define FAIL 0
#define SUCCESS 1

/*
 * 콘솔 색 바꿀 때 사용하는 색상의 값
 * enum으로 선언하여 코드의 가독성을 높인다.
 * typedef로 Color라고 타입을 지정한다.
 */
typedef enum Color {
    BLACK, BLUE, GREEN, CYAN, RED, PURPLE, YELLOW, WHITE, GRAY,
    LIGHT_BLUE, LIGHT_GREEN, LIGHT_CYAN, LIGHT_RED, LIGHT_PURPLE, LIGHT_YELLOW,
    DARK_WHITE
} Color;

// Snake의 구조체
// typedef로 Snake라고 타입을 지정한다.
typedef struct Snake {
    int x, y;       // 뱀 머리의 위치를 저장하는 변수
    int length;     // 뱀의 길이를 저장하는 변수
    int direction;  // 뱀의 방향을 저장하는 변수
} Snake;

// User의 구조체
// typedef로 User라고 타입을 지정한다.
typedef struct User {
    char name[500]; // 사용자의 이름
    int score;      // 사용자의 점수
} User;

Snake snake;
int board[BOARD_HEIGHT][BOARD_WIDTH];

void setCursorVisible(int);
inline void setCoordinate(int, int);
void setAttribute(int, int);

void print(const char*, int, int);

void MainScreen();
void GameScreen();
void initBestScore();
void initGame();
void initSnake();
void initBoard();
void initDeveloper();
void makeCoin();
void makeHole();
void drawBoard();
int move();
void EndScreen();
void sortUser(User[]);

int main() {
    // 랜덤 함수의 시드 값을 현재 시간으로 바꿔준다.
    // -> 안바꾸면 항상 같은 난수가 발생
    // -> 랜덤을 최대한 보장하기 위한 수단
    const unsigned int seed = time(NULL);
    srand(seed);

    // gcc 환경에서 한글, 특수문자를 출력하기 위한 명령어
    // 리눅스의 export LANG 과 비슷한 효과를 내는 명령어
    // 65001 = UTF-8
    // system("chcp 65001");
    setCursorVisible(0);

    MainScreen();
    return 0;
}

void setCursorVisible(int visibility) {
    /*
     * 커서의 보임/숨김을 설정하는 함수.
     * 게임할 때 커서를 숨기고, 입력을 받을 때 커서를 보여준다.
     * windows.h에서 제공하는 함수를 사용한다.
     */
    CONSOLE_CURSOR_INFO cursorInfo;
    cursorInfo.bVisible = visibility; // 1이면 보임, 0이면 숨김
    cursorInfo.dwSize = 1; // 커서의 두께

    SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &cursorInfo);
}
inline void setCoordinate(int x, int y) {
    /*
     * 커서의 위치를 설정하는 함수.
     * 뱀의 움직에 맞춰 머리를 출력할 때 사용한다.
     * windows.h에서 제공하는 함수를 사용한다.
     */
    COORD pos = { 2 * x, y };
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), pos);
}

void setAttribute(int text, int background) {
    /*
     * 출력할 텍스트의 색을 바꾸는 함수이다.
     * UI를 좀 더 표현하기 위해 사용한다.
     * windows.h에서 제공하는 함수를 사용한다.
     */
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), background * 16 + text);
}
void print(const char* str, int x, int y) {
    /*
     * 커서를 움직이고 출력하는 함수
     * 이 프로젝트에서는 위치에 맞게 출력하는 경우가 많기 때문에
     * printf대신 함수를 만들어서 출력한다.
     */
    setCoordinate(x, y);
    printf("%s", str);
}

void MainScreen() {
    /*
     * 시작 화면을 담당하는 함수
     */


    setCursorVisible(0);
    setAttribute(BLACK, BLACK);
    system("cls");  // 화면을 지우는 system 명령어


    setAttribute(BLACK, DARK_WHITE);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 0);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 1);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 2);
    print("　　　　　　　　　　　　Ｓｎａｋｅ　　Ｇａｍｅ　　　　　　　　　　　　", 0, 3);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 4);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 5);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 6);
    print("　　　　　　Ｐｒｅｓｓ　Ａｎｙ　Ｋｅｙ　Ｔｏ　Ｓｔａｒｔ　　　　　　　", 0, 7);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 8);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 9);
    print("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　", 0, 10);


    // 뱀을 오른쪽으로 움직이는 에니메이션
    setAttribute(CYAN, DARK_WHITE);
    print("●▷", 0, 10);
    for (int i = 1; i <= 10; ++i) {
        Sleep(1000); // 1초 대기하는 함수
        if (_kbhit()) {
            /*
             * _kbhit는 키보드의 입력이 있는지 확인하는 함수이다.
             * 키보드의 입력을 받은 경우 GameScreen를 실행한다.
             */
            _getch();
            GameScreen();
        }
        print("●▷", i, 10);
    }

    // 뱀을 위로 움직이는 에니메이션
    for (int i = 9; i >= 0; --i) {
        Sleep(1000); // 1초 대기하는 함수
        if (_kbhit()) {
            /*
             * _kbhit는 키보드의 입력이 있는지 확인하는 함수이다.
             * 키보드의 입력을 받은 경우 GameScreen를 실행한다.
             */
            _getch();
            GameScreen();
        }
        print("△", 11, i);
        print("●", 11, i + 1);
    }
}

void GameScreen() {
    /*
     * 플레이 화면을 담당하는 함수
     */

     // 화면을 지운다.
    setAttribute(BLACK, BLACK);
    system("cls");

    // 게임 시작할 때 필요한 변수들을 초기화한다.
    initGame();

    // 게임이 끝날 때 까지 무한루프
    while (true) {
        makeHole(); // 구멍을 만드는 함수
        drawBoard(); // 보드를 그리는 함수

        // 뱀이 움직이는 시간
        // 뱀의 길이가 길수록 시간이 짧아진다.
        Sleep(200 - 10 * (snake.length / 5));

        if (_kbhit()) {
            int ch = _getch();
            if (ch == 224) {
                // 방향키를 입력한 경우 224와 방향키의 정보가 들어온다.
                // 224인경우 한번더 _getch를해서 값을 가져오고 뱀의 방향을 바꾼다.
                ch = _getch();
                snake.direction = ch;
            }
        }

        // 움직임이 실패했을 때 -> 죽었을 때
        // EndScreen을 실행하고 반복문을 탈출한다.
        if (move() == FAIL) {
            EndScreen();
            break;
        }
    }
}

void initGame() {
    /*
     * 게임을 시작할 때 초기화 하는 함수
     */
    initDeveloper();
    initBestScore();
    initSnake();
    initBoard();
    makeCoin();
}

void initDeveloper() {
    /*
     * 플레이 화면에서 Made By 창을 출력하는 함수
     */
    setAttribute(BLACK, DARK_WHITE);
    print("┌───────────────────────┐", BOARD_WIDTH + 2, 12);
    print("│                       │", BOARD_WIDTH + 2, 13);
    print("│        Made By        │", BOARD_WIDTH + 2, 14);
    print("│                       │", BOARD_WIDTH + 2, 15);
    print("│     KANG TAE JONG     │", BOARD_WIDTH + 2, 16);
    print("│                       │", BOARD_WIDTH + 2, 17);
    print("└───────────────────────┘", BOARD_WIDTH + 2, 18);
}

void initBestScore() {
    /*
     * 플레이 화면에서 Best Score를 출력하는 함수
     */
    setAttribute(BLACK, DARK_WHITE);
    print("┌───────────────────────┐", BOARD_WIDTH + 2, 2);
    print("│                       │", BOARD_WIDTH + 2, 3);
    print("│      Best  Score      │", BOARD_WIDTH + 2, 4);
    print("│                       │", BOARD_WIDTH + 2, 5);
    print("│                       │", BOARD_WIDTH + 2, 6);
    print("│                       │", BOARD_WIDTH + 2, 7);
    print("│                       │", BOARD_WIDTH + 2, 8);
    print("│                       │", BOARD_WIDTH + 2, 9);
    print("└───────────────────────┘", BOARD_WIDTH + 2, 10);

    /*
     * best_score.txt를 읽고 Best Score을 출력하는 부분
     */
    char names[3][500], scores[3][500];
    FILE* f = fopen("best_score.txt", "r");
    if (f == NULL) {
        /*
         * 파일 읽기를 실패한 경우
         * -> 파일이 없는 경우
         * 쓰기 모드로 파일을 읽고
         * 1, 2, 3등을 None과 0점으로 저장한다.
         */
        FILE* file = fopen("best_score.txt", "w");
        for (int i = 0; i < 3; ++i) {
            fprintf(file, "None\n");
            fprintf(file, "0\n");

            strcpy(names[i], "None");
            strcpy(scores[i], "0");
        }

        fclose(file);
    }
    else {
        for (int i = 0; i < 3; ++i) {
            fscanf(f, "%s %s", names[i], scores[i]);
        }

        fclose(f);
    }

    for (int i = 0; i < 3; ++i) {
        /*
         * str에 출력 형식을 맞게 저장하고 출력한다.
         */
        char str[500] = { 0 };
        _itoa(i + 1, str, 10);   // 등수
        strcat(str, ". ");      // 등수뒤에 .
        strcat(str, names[i]);  // 이름
        strcat(str, " : ");     // 이름과 점수 사이 :
        strcat(str, scores[i]); // 점수

        print(str, BOARD_WIDTH + 4, 6 + i);
    }
}

void initSnake() {
    /*
     * 뱀을 초기화하는 함수
     * 뱀의 시작 위치, 길이, 방향을 초기화한다.
     */
    snake.x = 5;
    snake.y = BOARD_HEIGHT / 2;
    snake.length = 1;
    snake.direction = RIGHT;
}

void initBoard() {
    /*
     * 보드를 초기화하는 함수
     * 벽, 빈 공간, 뱀의 위치를 저장한다.
     */

     // 빈 공간
    for (int x = 0; x < BOARD_WIDTH; ++x) {
        for (int y = 0; y < BOARD_HEIGHT; ++y) {
            board[y][x] = EMPTY;
        }
    }

    // 가로 벽
    for (int x = 0; x < BOARD_WIDTH; ++x) {
        board[0][x] = WALL;
        board[BOARD_HEIGHT - 1][x] = WALL;
    }

    // 세로 벽
    for (int y = 0; y < BOARD_HEIGHT; ++y) {
        board[y][0] = WALL;
        board[y][BOARD_WIDTH - 1] = WALL;
    }

    // 뱀의 위치
    board[snake.y][snake.x] = snake.direction;
}

void makeCoin() {
    /*
     * 코인을 만드는 함수
     */
    do {
        /*
         * rand()값을 통해 랜덤으로 x, y의 좌표를 구한다.
         * x, y의 값이 EMPTY면 코인으로 바꾼다.
         */
        int x = rand() % BOARD_WIDTH;
        int y = rand() % BOARD_HEIGHT;

        if (board[y][x] == EMPTY) {
            board[y][x] = COIN;
            break;
        }
    } while (true);
}

void makeHole() {
    /*
     * 구멍을 만드는 함수
     */

     // time을 static으로 선언한다.
     // -> 함수가 끝나도 변수의 값을 보존하기 위해
     // 기다리는 시간을 구해서 더한다.
    static int time;
    time += 200 - 10 * (snake.length / 5);

    // 기다린 시간이 5초가 넘으면
    // 구멍을 만들고 기다린 시간에 5초를 뺀다.
    if (time >= 5000) {
        time -= 5000;
        do {
            /*
             * rand()값을 통해 랜덤으로 x, y의 좌표를 구한다.
             * x, y의 값이 EMPTY면 구멍으로 바꾼다.
             */
            int x = rand() % BOARD_WIDTH;
            int y = rand() % BOARD_HEIGHT;

            if (board[y][x] == EMPTY && abs(snake.x + x) + abs(snake.y - y) >= 3) {
                board[y][x] = HOLE;
                break;
            }
        } while (true);
    }
}

void drawBoard() {
    /*
     * 보드를 그리는 함수
     * switch문을 사용하여 상황에 맞게 출력한다.
     * ▩ : 벽 -> 흰색
     *    : 빈 공간 -> 검은색
     * ★ : 코인 -> 노란색
     * ▣ : 구멍 -> 빨간색
     * △,◁,▷,▽ : 뱀의 머리 -> 파란색
     * ● : 뱀의 몸 -> 파란색
     */
    for (int y = 0; y < BOARD_HEIGHT; ++y) {
        for (int x = 0; x < BOARD_WIDTH; ++x) {
            switch (board[y][x]) {
            case WALL:
                setAttribute(BLACK, DARK_WHITE);
                print("▩", x, y);
                break;
            case EMPTY:
                setAttribute(BLACK, BLACK);
                print("  ", x, y);
                break;
            case COIN:
                setAttribute(LIGHT_YELLOW, BLACK);
                print("★", x, y);
                break;
            case HOLE:
                setAttribute(RED, BLACK);
                print("▣", x, y);
                break;
            default:
                setAttribute(CYAN, BLACK);
                if (x == snake.x && y == snake.y) {
                    switch (snake.direction) {
                    case UP:
                        print("△", snake.x, snake.y);
                        break;
                    case LEFT:
                        print("◁", snake.x, snake.y);
                        break;
                    case RIGHT:
                        print("▷", snake.x, snake.y);
                        break;
                    case DOWN:
                        print("▽", snake.x, snake.y);
                        break;
                    }
                }
                else {
                    print("●", x, y);
                }

                break;
            }
        }
    }
}

int move() {
    /*
     * 뱀의 움직임을 담당하는 함수
     */

     // 뱀의 방향에 맞춰 뱀의 머리 위치를 계산한다.
     // UP : y의 값을 1 감소
     // LEFT : x의 값을 1감소
     // RIGHT : x의 값을 1증가
     // DOWN : y의 값을 1증가
    switch (snake.direction) {
    case UP:
        snake.y--;
        break;
    case LEFT:
        snake.x--;
        break;
    case RIGHT:
        snake.x++;
        break;
    case DOWN:
        snake.y++;
        break;
    }

    // 뱀의 움직인 후의 위치에 맞게 처리하는 부분
    // EMPTY : 뱀의 머리로 바꿈
    // COIN : 뱀의 길이를 증가하고, 머리로 바꿈
    // 그 밖(벽, 구멍) : FAIL을 반환 (실패)
    switch (board[snake.y][snake.x]) {
    case EMPTY:
        board[snake.y][snake.x] = snake.direction;
        break;
    case COIN:
        board[snake.y][snake.x] = snake.direction;
        snake.length++;
        makeCoin();
        break;
    default:
        return FAIL;
    }

    // 뱀의 꼬리를 찾고 빈 공간으로 바꾸는 부분
    // 뱀의 몸과 머리를 방향 값으로 저장했기 때문에
    // 역으로 추적하여 뱀의 꼬리 부분을 찾는다.
    // UP : y의 값을 1 증가
    // LEFT : x의 값을 1 증가
    // RIGHT : x의 값을 1 감소
    // DOWN : y의 값을 1 감소
    int x = snake.x, y = snake.y;
    for (int i = 0; i < snake.length; ++i) {
        switch (board[y][x]) {
        case UP:
            y++;
            break;
        case LEFT:
            x++;
            break;
        case RIGHT:
            x--;
            break;
        case DOWN:
            y--;
            break;
        }
    }
    board[y][x] = EMPTY;

    return SUCCESS;
}

void EndScreen() {
    /*
     * 게임이 끝났을 때를 담당하는 함수
     * Win, Lose를 판단한다.
     * Win일 경우 best_score을 변경한다.
     * Lose일 경우 시작 화면으로 돌아간다.
     */

     // best score을 불러오는 부분
    char names[3][500] = { 0 };
    char scores[3][500] = { 0 };
    FILE* f = fopen("best_score.txt", "r");
    for (int i = 0; i < 3; ++i) {
        fscanf(f, "%s %s", names[i], scores[i]);
    }

    fclose(f);

    setAttribute(BLACK, DARK_WHITE);
    int bestScore = atoi(scores[2]);
    if (bestScore < snake.length) {
        /*
         * best score을 갱신했을 때
         */
        print("┌──────────────────────────────────────┐", 5, 5);
        print("│                                      │", 5, 6);
        print("│               You Win!               │", 5, 7);
        print("│                                      │", 5, 8);
        print("│                                      │", 5, 9);
        print("│                                      │", 5, 10);
        print("│                                      │", 5, 11);
        print("│                                      │", 5, 12);
        print("│                                      │", 5, 13);
        print("└──────────────────────────────────────┘", 5, 14);

        // 점수를 문자열로 변경하여 출력하는 부분
        char score[100];
        _itoa(snake.length, score, 10);  // 점수를 문자열로 바꿈
        print("│     Your Score : ", 5, 9);
        printf("%s", score);

        // 이름을 입력받는 부분.
        char user[500];
        print("│     What Your Name? ", 5, 12);
        setCursorVisible(1);    // 커서를 보이게 한다.
        scanf("%s", user);              // 입력을 받는다.
        setCursorVisible(0);    // 커서를 다시 숨긴다.
        print("│     Press Any Key To Continue...     │", 5, 12);

        // users에 best_score에 저장된 값과
        // 새로 갱신한 값을 저장하여 정렬한다.
        User users[4];
        for (int i = 0; i < 3; ++i) {
            // best_score에 저장된 값을 users에 저장
            strcpy(users[i].name, names[i]);
            users[i].score = atoi(scores[i]);
        }

        // 새로 갱신한 값을 users에 저장
        strcpy(users[3].name, user);
        users[3].score = snake.length;
        sortUser(users);    // users정렬


        // 새로 갱신된 users를 best_score에 저장한다.
        f = fopen("best_score.txt", "w");
        for (int i = 0; i < 3; ++i) {
            fprintf(f, "%s\n", users[i].name);
            fprintf(f, "%d\n", users[i].score);
        }

        fclose(f);
    }
    else {
        /*
         * best score을 갱신하지 못했을 때
         */
        print("┌──────────────────────────────────────┐", 5, 5);
        print("│                                      │", 5, 6);
        print("│               You Lose               │", 5, 7);
        print("│                                      │", 5, 8);
        print("│                                      │", 5, 9);
        print("│                                      │", 5, 10);
        print("│                                      │", 5, 11);
        print("│     Press Any Key To Continue...     │", 5, 12);
        print("│                                      │", 5, 13);
        print("└──────────────────────────────────────┘", 5, 14);

        char score[100];
        _itoa(snake.length, score, 10);
        int len = strlen(score);
        print("│     Your Score : ", 5, 9);
        printf("%s", score);
    }

    int ch = _getch();
    if (ch == 224) {
        _getch();
    }

    MainScreen();
}

void sortUser(User arr[]) {
    /*
     * best score을 저장하기 위해 user를 정렬하는 함수
     * 버블소트를 사용한다.
     */
    for (int i = 1; i < 4; ++i) {
        for (int j = i; j >= 1; --j) {
            if (arr[j].score > arr[j - 1].score) {
                User temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
            else {
                break;
            }
        }
    }
}