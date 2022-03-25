package com.example.teachagram.load;

import com.example.teachagram.daos.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class CambridgeAdditionalMathematics implements StandardModel {

    static final String name = "Cambridge Additional Mathematics";
    static final String[] units = {"A1", "A2", "A3", "Á4", "A5", "A6", "A7", "A8", "Á9", "A10", "A11", "A12", "A13", "Á14"};
    static final List<String[]> objectives = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getUnit() {
        return units;
    }

    @Override
    public List<String[]> getObjective() {
        return objectives;
    }

    public CambridgeAdditionalMathematics() {


        //A1
        objectives.add(new String[]{"A1.1,Understand the terms: function, domain, range (image set), one-one function, inverse function and composition of functions.",
                "A1.2,Use the notations of functions: f(x)=sin x, f:x--logx, f(-1)(x).",
                "A1.3,Understand the relationship between y=f(x) and y=|f(x)|, where f(x) is linear, quadratic, of trigonometric.",
                "A1.4,Explain in words why a given function is a function or why it does not have an inverse.",
                "A1.5,Find the inverse of a one-one function and form composite functions."
                , "A1.6Use sketch graphs to show the relationship between a function and its inverse."
        });

        //A2
        objectives.add(new String[]{"A2.1,Find the maximum or minimum value of the quadratic function f:x--ax²+bx+c.",
                "A2.2,Use the maximum or minimum value of f(x) to sketch the graph or determine the range for a given domain.",
                "A2.3,Know the conditions for f(x) = 0 to have;´\n(i) two real roots\n(ii) two equal roots\n(iii) no real roots\n" +
                        "and the related conditions for a given line to\n(i) intersect a given curve\n(ii) be a tangent to a given curve\n(iii) not intersect a given curve."
        });
        //A3
        objectives.add(new String[]{"A3.1", "A3.2", "A3.3,Use substitution to form and solve a quadratic equation in order to solve a related equation.",
                "A3.4,Sketch the graphs of cubic polynomials and their moduli, when given in factorized form y = k(x-a)(x-b)(x-c)."
                , "A3.5,Solve cubic inequalities in the form k(x-a)(x-b)(x-c) â‰¤ d graphically."});
        //A4
        objectives.add(new String[]{"A4.1,Perform simple operations with indices and with surds, including rationalizing the denominator.",
                ""});
        //A5
        objectives.add(new String[]{"A5.1,Know and use the remainder and factor theorems.",
                "A5.2,Find factors of polynomials.",
                "A5.3,Solve cubic equations."});
        //A6
        objectives.add(new String[]{"A6.1,Solve simple simultaneous equations in two unknowns by elimination or substitution."
                , ""});
        //A7
        objectives.add(new String[]{"A7.1,\"Know simple properties and graphs of the logarithmic and exponential functions including lnx and e^x " +
                "(series expansions are not required) and graphs of ke^(nx)+a and k ln(ax+b), where n, k, a and b are integers.",
                "A7.3,Solve equations of the form: a^x = b."});
        //A8
        objectives.add(new String[]{"A8.1,Interpret the equation of a straight line graph in the form y = mx+c.",
                "A8.2,\"Transform given relationships, including y = ax^n and y = Ab^x to straight line form and" +
                        " hence determine unknown constants by calculating the gradient or intercept of the transformed graph.\""
                , "A8.3,Solve questions involving mid-point and length of a line.",
                "A8.4,Know and use the condition for two lines to be parallel or perpendicular, including finding the equation of perpendicular bisectors."});
        //A9
        objectives.add(new String[]{"A9.1,Solve problems involving the arc length and sector area of a circle, including knowledge and use of radian measure."});
        //A10
        objectives.add(new String[]{"A10.1,Know the six trigonometric functions of angles of any magnitude (sine, cosine, tangent, secant, cosecant, cotangent)."
                , "A10.2,Understand amplitude and periodicity and the relationship between graphs of related trigonometric functions, e.g. sin x and sin 2x.",
                "A10.3,Draw and use the graphs of:\ny = a sin bx + c \ny = a cos bx + c\nv = a tan bx + c\nwhere a is a positive integer, b is a simple fraction " +
                        "or integer (fractions will have a denominator of 2, 3, 4, 6 or 8 only), and c is an integer."
                , "A10.4,Use the relationships:\nsin² A + cos² A = 1\nsec² A = 1 + tan² A\n cosec² A = 1 + cot² A\n(sin A)/(cos A)= tan A, (cos A)/(sin A)=cot A."
                , "A10.5,Solve simple trigonometric equations involving the six trigonometric functions and the above relationships (not including general solution of trigonometric equations)."
                , "A10.6,Prove simple trigonometric identities."});
        //A11
        objectives.add(new String[]{"A11.1,Recognize and distinguish between a permutation case and a combination case.",
                "A11.2,Know and use the notation n! (with 0! = 1), and the expressions for permutations and combinations of n items taken r at a time."
                , "A11.3,\"Answer simple problems on arrangement and selection (cases with repetition of objects, or with objects arranged in a circle, " +
                "or involving both permutations and combinations, are excluded)."});
        //A12
        objectives.add(new String[]{"A12.1,Use the Binomial Theorem for expansion of (a + b)^n for positive integer n.",
                "A12.2,Use the general term nCr a^(n-r) b^r, 0 <= r <= n (knowledge of the greatest term and properties of the coefficients is not required)."
                , "A12.3,Recognize arithmetic and geometric progressions.",
                "A12.4,Use the formulae for the nth term and for the sum of the first n terms to solve problems involving arithmetic or geometric progressions."
                , "A12.5,Use the condition for the convergence of a geometric progression, and the formula for the sum to infinity of a convergent geometric progression."});
        //A13
        objectives.add(new String[]{"A13.1,Use vectors in any form, e.g. (a b), â†’{AB}, p, ai+bj.",
                "A13.2,Know and use position vectors and unit vectors.",
                "A13.3,Find the magnitude of a vector\nadd and subtract vectors and multiply vectors by scalars.",
                "A13.4,Compose and resolve velocities."});
        //A14
        objectives.add(new String[]{"A14.1,understand the idea of a derived function."
                , "A14.2,Use the notations: f'(x), f''(x), dy/dx, (d^2y)/(dx^2) [=d/dx (dy/dx)]."
                , "A14.3,Use the derivatives of the standard functions x^n (for any rational n), sin x, cos x, tan x," +
                " e^x, In x, together with constant multiples, sums and composite functions of these."
                , "A14.4,Differentiate products and quotients of functions.",
                "A14.5,Apply differentiation to gradients, tangents and normals, stationary points," +
                        " connected rates of change, small increments and approximations and practical maxima and minima problems."
                , "A14.6,Use the first and second derivative tests to discriminate between maxima and minima."
                , "A14.7,Understand integration as the reverse process of differentiation."
                , "A14.8,integrate sums of terms in powers of x including 1/x and 1/(ax+b)."
                , "A14.9,Integrate functions of the form (ax + b)^n for any rational n, sin(ax + b), cos(ax + b), e^(ax + b)."
                , "A14.10,Evaluate definite integrals and apply integration to the evaluation of plane areas.",
                "A14.11,Apply differentiation and integration to kinematics problems that involve displacement, velocity and acceleration of a " +
                        "particle moving in a straight line with variable or constant acceleration, and the use of x-t and v-t graphs."});

    }


}

