Jerly
=====

A Java implementation of Earley’s efﬁcient parsing algorithm for lambda-free context-free grammars. The name comes from J-Earley.

Originally, I wrote this project in 2004-2005 when I was in France.
I decided to put it on GitHub in 2017. I have no intention to maintain this project, so don't send me any pull requests. If you
want to add / modify something, feel free to make a fork.

In 2017 I tried this project and it still works fine.

I also wrote a short (unpublished) paper about the Earley algorithm,
see the file `jerly.pdf`. It explains the algorithm, and it also
presents this software shortly. It also has an example.

Usage
-----

    Usage: jerly [switches] <rule_file> <question_file>
    Switches:
       --help                          help information (you can see this now)
                                       can be used _alone_
       --version, -V                   version information
                                       can be used _alone_

       rule_file                       rules of the grammar
       question_file                   question (word)

How to try it right away

    ./start input/paper.txt input/question.txt

    Rules:
    ======
    S->S+A
    S->A
    A->AxB
    A->B
    B->(s)
    B->a


    Question:
    =========
    axa+a

    The word 'axa+a' can be generated with the grammar.

    HTML output was successfully written to dir. 'output'.

The software also creates an HTML output with a nice table.
It can be found in the `output` folder (see [screenshot](output/screenshot.jpg)).

Author
------

* Laszlo Szathmary
* jabba.laci@gmail.com (this is my current e-mail)
