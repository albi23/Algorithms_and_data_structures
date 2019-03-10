/*
 * Author Albert Piekielny
 * An example of implementation in a C language of a one-way list
*/

#include <stdio.h>
#include <stdlib.h>

/*Licznik opperacji porównań przy usuwaniau/wstawianiu*/
int counter = 0;

/* Struktura listy, zawierająca wskaźnik do next listy i element typu int*/
typedef struct List {
    int item;
    struct List *next;
} List;

int isempty(List **list);

/*Funkcja odpowiedzialna za wstawianie elementów do listy*/
void insert(List **list, int number) {

    /*Tworząc nowy element listy ustawiamy, że "nie ma dzieci"*/
    List *new = (List *) malloc(sizeof(List));
    new->next = NULL;
    new->item = number;

    if ((*list) == NULL) {
        *list = new;
    } else {

        List *lastItemOfList = *list;

        while (lastItemOfList->next != NULL) {
            lastItemOfList = lastItemOfList->next;
        }
        lastItemOfList->next = new;
    }
    printf("New number: %d  was succesfully added \n\n", new->item);

}

/*Funkcja prezentująca zawartość listy*/
void printResult(List *list) {

    printf("\nList{");
    while (list != NULL) {
        printf("%d,", list->item);
        list = list->next;
    }
    printf("} \n\n");
}

/* Funkcja odpowiedzialna za usuwanie elementów z listy*/
int delete(List **list, int chosedNumber) {

    List *before = NULL;
    List *ptr = *list;

    if (isempty(list)) {
        printf("List is empty, nothing to delete.\n\n");
        counter++;
        return 0;
    }

    int tempNumber = (*ptr).item;

    while (tempNumber != chosedNumber && ptr->next != NULL) { /* szukamy pierwszego wystąpienia zadanej cyfry*/
        before = ptr;
        ptr = ptr->next;
        tempNumber = ptr->item;
        counter++;
    }

    /*Warunek sprawdzający czy na liście występuje szukany element*/
    if (ptr->next == NULL && tempNumber != chosedNumber) {
        printf("List do not contains element : %d.\n\n", chosedNumber);
        counter++;
        return 0;
    }
    /*Pierwszy if odpowiada za usunięcie pierwszego eementu listy - zwyczajne przepiecie wsk na drugi "element"*/
    if (before == NULL) {
        counter++;
        (*list) = (*list)->next;
        free(ptr);
    } else {
        counter++;
        before->next = ptr->next;
        free(ptr);
    }
    printf("The item: %d has been removed\n\n", chosedNumber);
    return 1;
}

/*Funkcja sprawdzająca czy lista jest pusta*/
int isempty(List **list) {
    counter++;
    if (*list == NULL) {
        return 1;
    }
    return 0;
}

/* Funkcja sprawdzająca czy podany element jest na liście i w przypadku jego znalezienia
    przesuwający go na przód listy; */
int findMTF(List **list,int chosedNumber ) {

    if (isempty(list)) {
        counter++;
        printf("List is empty, nothing to search.\n\n");
        return 0;
    }

    List *searchedChild = NULL;
    List *ptr = *list;
    int number = ptr->item;

    if (number == chosedNumber){
        counter++;
        return 0;
    }

    while (ptr->next != NULL && number != chosedNumber) {
        searchedChild = ptr;
        ptr = ptr->next;
        number = ptr->item;
        counter++;
    }
    if (ptr->next == NULL && number != chosedNumber) {
        printf("List do not contains element: %d.\n\n", chosedNumber);
        counter++;
        return 0;
    }

    printf("number  %d\n", ptr->item);
    searchedChild->next = ptr->next;

    ptr->next = *list;
    *list = ptr;
    return 0;
}


/*Funkcja sprawdzająca czy podany element jest na liście i w przypadku jego znalezienia
  przesuwający go o jedno miejsce do przodu listy.*/
int findTRANS(List **list, int chosedNumber) {

    if (isempty(list)) {
        counter++;
        printf("List is empty, nothing to search.\n\n");
        return 0;
    }

    List *searchedChild = NULL;
    List *ptr = *list;
    int number = ptr->item;

    /*Jeśli element jest pierwszy na liście nic nie robimy*/
    if (number == chosedNumber){
        counter++;
        return 0;
    }
    /*Przeszukujemy listę w celu znalezienia żądanej liczby*/
    while (ptr->next != NULL && number != chosedNumber) {
        searchedChild = ptr;
        ptr = ptr->next;
        number = ptr->item;
        counter++;
    }
    if (ptr->next == NULL && number != chosedNumber) {
        printf("List do not contains element: %d.\n\n", chosedNumber);
        counter++;
        return 0;
    }
    ptr->item = searchedChild->item;
    searchedChild->item = chosedNumber;

    return 0;

}
/*Pobieramy i zwracamy liczbę wprowadzoną przez użytkownika*/
int getNumber(char *description){
    int number;
    printf("%s",description);
    scanf("%d", &number);
    return number;
}

/*Metoda wstawia 100 losowych elementów od 1 do 100,
  następnie sprawdza czy element występuje na liście i ostatecznie
  do póki lista nie będzie pusta usuwa el. maxymalny**/

void testOfWorking(List **list){

    int elementsToAdd[100];
    for(int i = 0;i<100;++i)elementsToAdd[i]=i+1;

    for(int i = 0; i< 100; ++i){
        int randIndex =  rand() % 100;
        int tempValue = elementsToAdd[i];
       elementsToAdd[i] = elementsToAdd[randIndex];
       elementsToAdd[randIndex]= tempValue;
    }

    for(int i= 0; i< 100; ++i)insert(list,elementsToAdd[i]);

    List *ptr = *list;
    int max= 0;
    while (!isempty(list)){
        for(int i = 1;i<=100;++i) findTRANS(list,i);
        while (ptr != NULL){
            if(ptr->item > max) max= ptr->item;
            printf("(%d,%d) ",ptr->item,max);
            ptr = ptr->next;
            counter++;
        }
        delete(list, max);      /*Usuwamy element max*/
        ptr = *list;            /*ustawiamy wskaźnik na początek listy*/
        max = 0;                /*zereujemy wartosć maxymalną*/
    }
    printf("counter: %d \n", counter);
}

/**Główna funkcja programu*/
int main(int arg, char **argc) {

    /**Nasza początkowa lista */
    List *mainList = NULL;
    int choice;

    testOfWorking(&mainList);

    /**Główna pętla programu*/
    while (choice != 0) {

        printf("Chose:  \n");
        printf("      0 to quit from program. \n");
        printf("      1 to insert one item. \n");
        printf("      2 to check elements of list. \n");
        printf("      3 to delete elements from list. \n");
        printf("      4 to check if list is empty \n");
        printf("      5 to find element and put it on top\n");
        printf("      6 to find element and increse postion about 1\n");


        scanf("%d", &choice);

        switch (choice) {
            case 0:
                return 0;

            case 1:
                insert(&mainList,getNumber("Please enter new number: "));
                break;

            case 2:
                printResult(mainList);
                break;

            case 3:
                delete(&mainList,getNumber("Please chose number to delete: "));
                break;

            case 4:
                printf("\nList is empty?: %d\n\n", isempty(&mainList));
                break;

            case 5:
                findMTF(&mainList,getNumber("Please chose number to search: "));
                break;

            case 6:
                findTRANS(&mainList,getNumber("Please chose number to search: "));
                break;
        }
    }

    return 0;
}
