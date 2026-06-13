# ---------- Constants ----------
NUM_REVENUE_ENTRIES = 100
PROFIT_ENTRIES = 12
QUARTERS_PER_YEAR = 4.0
EXPENSE_TYPE_1 = 1
EXPENSE_TYPE_2 = 2
EXPENSE_TYPE_3 = 3
SUCCESS = 0


# ---------- Functional: zero out & load expense data ----------
def initRevenueExpense(inputRec, quarter):
    for i in range(NUM_REVENUE_ENTRIES):
        inputRec.revenue[i] = 0
        inputRec.expense[i] = corpExpense[quarter][i]


# ---------- Functional: persist employee record ----------
def updateCorpDatabase(empRec):
    # existing DB-update logic unchanged
    pass


# ---------- Functional (sequential): compute estimated revenue ----------
def calculateEstimatedRevenue(ytdRevenue, quarter):
    if quarter == 0:
        raise ValueError("quarter must not be zero")
    return ytdRevenue * QUARTERS_PER_YEAR / quarter


# ---------- Functional: copy color, set status ----------
def updateColorAndStatus(prevColor):
    newColor = prevColor
    status = SUCCESS
    return newColor, status


# ---------- Logical (acknowledged smell): compute profit by expense type ----------
def calculateProfit(expenseType, revenue, expense):
    profit = [0] * PROFIT_ENTRIES

    if expenseType == EXPENSE_TYPE_1:
        for i in range(PROFIT_ENTRIES):
            profit[i] = revenue[i] - expense.type1[i]

    elif expenseType == EXPENSE_TYPE_2:
        for i in range(PROFIT_ENTRIES):          # fixed: missing loop added
            profit[i] = revenue[i] - expense.type2[i]

    elif expenseType == EXPENSE_TYPE_3:
        for i in range(PROFIT_ENTRIES):          # fixed: missing loop added
            profit[i] = revenue[i] - expense.type3[i]

    return profit


# ---------- Orchestrating routine (replaces HandleStuff) ----------
def processQuarterlyData(inputRec, quarter, empRec, ytdRevenue,
                          prevColor, expenseType):
    if quarter == 0:
        raise ValueError("quarter must not be zero")

    initRevenueExpense(inputRec, quarter)
    updateCorpDatabase(empRec)
    estimatedRevenue = calculateEstimatedRevenue(ytdRevenue, quarter)
    newColor, status = updateColorAndStatus(prevColor)
    profit = calculateProfit(expenseType, inputRec.revenue, expenseData)

    return estimatedRevenue, newColor, status, profit