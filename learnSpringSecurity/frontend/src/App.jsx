import { useState, useEffect } from "react";

const BASE = "http://localhost:8080";

function authHeaders(token) {
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
}

// ─── Reusable Components ───────────────────────────────────────────

function Badge({ type, children }) {
  const styles = {
    info:    { background: "var(--color-background-info)",    color: "var(--color-text-info)" },
    success: { background: "var(--color-background-success)", color: "var(--color-text-success)" },
    warning: { background: "var(--color-background-warning)", color: "var(--color-text-warning)" },
    danger:  { background: "var(--color-background-danger)",  color: "var(--color-text-danger)" },
  };
  return (
    <span style={{ fontSize: 11, padding: "3px 8px", borderRadius: 20, fontWeight: 500, ...styles[type] }}>
      {children}
    </span>
  );
}

function Card({ children, style }) {
  return (
    <div style={{
      background: "var(--color-background-primary)",
      border: "0.5px solid var(--color-border-tertiary)",
      borderRadius: "var(--border-radius-lg)",
      padding: "1rem 1.25rem",
      ...style,
    }}>
      {children}
    </div>
  );
}

function Btn({ onClick, primary, small, fullWidth, disabled, children }) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      style={{
        cursor: disabled ? "not-allowed" : "pointer",
        fontFamily: "var(--font-sans)",
        fontSize: small ? 13 : 14,
        padding: small ? "5px 12px" : "8px 16px",
        borderRadius: "var(--border-radius-md)",
        border: `0.5px solid ${primary ? "var(--color-text-primary)" : "var(--color-border-secondary)"}`,
        background: primary ? "var(--color-text-primary)" : "transparent",
        color: primary ? "var(--color-background-primary)" : "var(--color-text-primary)",
        width: fullWidth ? "100%" : "auto",
        opacity: disabled ? 0.6 : 1,
        transition: "opacity 0.15s",
      }}
    >
      {children}
    </button>
  );
}

function Navbar({ title, name, role, onLogout }) {
  return (
    <nav style={{
      background: "var(--color-background-primary)",
      borderBottom: "0.5px solid var(--color-border-tertiary)",
      padding: "0 1.5rem",
      height: 52,
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
    }}>
      <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="var(--color-text-primary)" strokeWidth="2">
          <path d="M12 2L2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/>
        </svg>
        <span style={{ fontSize: 15, fontWeight: 500 }}>{title}</span>
        {role === "ROLE_ADMIN" && <Badge type="warning">Admin</Badge>}
      </div>
      <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
        {name && <span style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>{name}</span>}
        <Btn small onClick={onLogout}>Sign out</Btn>
      </div>
    </nav>
  );
}

// ─── Login Page ────────────────────────────────────────────────────

function LoginPage({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError]       = useState("");
  const [loading, setLoading]   = useState(false);

  const inputStyle = {
    width: "100%",
    padding: "8px 12px",
    fontSize: 14,
    border: "0.5px solid var(--color-border-secondary)",
    borderRadius: "var(--border-radius-md)",
    background: "var(--color-background-primary)",
    color: "var(--color-text-primary)",
    fontFamily: "var(--font-sans)",
  };

  async function handleLogin() {
    if (!username || !password) { setError("Please enter email and password."); return; }
    setLoading(true); setError("");
    try {
      const res = await fetch(`${BASE}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      if (!res.ok) { setError("Invalid credentials. Please try again."); setLoading(false); return; }
      const data = await res.json();
      // data = { token: "eyJ...", role: "ROLE_USER" or "ROLE_ADMIN" }
      localStorage.setItem("jwt_token", data.token);
      localStorage.setItem("jwt_role",  data.role);
      onLogin(data.token, data.role);
    } catch {
      setError("Cannot reach server at localhost:8080. Is Spring Boot running?");
      setLoading(false);
    }
  }

  return (
    <div style={{ minHeight: "100vh", display: "flex", alignItems: "center", justifyContent: "center", padding: "2rem", background: "var(--color-background-tertiary)" }}>
      <div style={{ width: "100%", maxWidth: 360 }}>
        <div style={{ textAlign: "center", marginBottom: "1.5rem" }}>
          <div style={{ width: 40, height: 40, borderRadius: "var(--border-radius-md)", background: "var(--color-text-primary)", display: "inline-flex", alignItems: "center", justifyContent: "center", marginBottom: 12 }}>
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="var(--color-background-primary)" strokeWidth="2">
              <path d="M12 2L2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/>
            </svg>
          </div>
          <h1 style={{ fontSize: 20, fontWeight: 500 }}>Student Portal</h1>
          <p style={{ fontSize: 13, color: "var(--color-text-secondary)", marginTop: 4 }}>Sign in to continue</p>
        </div>

        <Card>
          <div style={{ marginBottom: 12 }}>
            <label style={{ fontSize: 13, color: "var(--color-text-secondary)", display: "block", marginBottom: 6 }}>Email / Username</label>
            <input style={inputStyle} value={username} onChange={e => setUsername(e.target.value)} placeholder="Deepansh@gmail.com" />
          </div>
          <div style={{ marginBottom: 16 }}>
            <label style={{ fontSize: 13, color: "var(--color-text-secondary)", display: "block", marginBottom: 6 }}>Password</label>
            <input style={inputStyle} type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="••••••••"
              onKeyDown={e => e.key === "Enter" && handleLogin()} />
          </div>
          {error && (
            <div style={{ fontSize: 13, color: "var(--color-text-danger)", background: "var(--color-background-danger)", padding: "8px 12px", borderRadius: "var(--border-radius-md)", marginBottom: 12 }}>
              {error}
            </div>
          )}
          <Btn primary fullWidth disabled={loading} onClick={handleLogin}>
            {loading ? "Signing in..." : "Sign in"}
          </Btn>
        </Card>

        <div style={{ marginTop: "1rem", background: "var(--color-background-secondary)", borderRadius: "var(--border-radius-md)", padding: 12, fontSize: 12, color: "var(--color-text-secondary)" }}>
          <p style={{ fontWeight: 500, color: "var(--color-text-primary)", marginBottom: 4 }}>What happens on login:</p>
          <p>1. POST /auth/login → Spring authenticates</p>
          <p>2. Response: {`{ token, role }`}</p>
          <p>3. Token saved to localStorage</p>
          <p>4. Role decides which page renders</p>
        </div>
      </div>
    </div>
  );
}

// ─── Student Dashboard ─────────────────────────────────────────────

function StudentPage({ token, onLogout }) {
  const [allCourses, setAllCourses]       = useState([]);
  const [enrolled, setEnrolled]           = useState([]);
  const [loading, setLoading]             = useState(true);
  const [enrolling, setEnrolling]         = useState(null);

  async function loadData() {
    setLoading(true);
    try {
      const [allRes, myRes] = await Promise.all([
        fetch(`${BASE}/courses`,            { headers: authHeaders(token) }),
        fetch(`${BASE}/students/me/courses`, { headers: authHeaders(token) }),
      ]);
      setAllCourses(await allRes.json());
      setEnrolled(await myRes.json());
    } catch { }
    setLoading(false);
  }

  useEffect(() => { loadData(); }, []);

  async function enroll(courseId) {
    setEnrolling(courseId);
    await fetch(`${BASE}/students/me/enroll/${courseId}`, { method: "POST", headers: authHeaders(token) });
    await loadData();
    setEnrolling(null);
  }

  const enrolledIds  = enrolled.map(c => c.courseId);
  const available    = allCourses.filter(c => !enrolledIds.includes(c.courseId));

  return (
    <div style={{ minHeight: "100vh", background: "var(--color-background-tertiary)" }}>
      <Navbar title="Student Portal" role="ROLE_USER" onLogout={onLogout} />
      <div style={{ maxWidth: 860, margin: "0 auto", padding: "1.5rem" }}>
        {loading ? (
          <p style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>Loading courses...</p>
        ) : (
          <>
            <h2 style={{ fontSize: 16, fontWeight: 500, marginBottom: "1rem" }}>My enrolled courses</h2>
            <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(220px,1fr))", gap: 12, marginBottom: "2rem" }}>
              {enrolled.length ? enrolled.map(c => (
                <Card key={c.courseId}>
                  <p style={{ fontSize: 14, fontWeight: 500, marginBottom: 8 }}>{c.courseName}</p>
                  <Badge type="success">Enrolled</Badge>
                </Card>
              )) : <p style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>No enrolled courses yet.</p>}
            </div>

            <h2 style={{ fontSize: 16, fontWeight: 500, marginBottom: "1rem", paddingTop: "1.5rem", borderTop: "0.5px solid var(--color-border-tertiary)" }}>
              Available courses
            </h2>
            <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(220px,1fr))", gap: 12 }}>
              {available.length ? available.map(c => (
                <Card key={c.courseId}>
                  <p style={{ fontSize: 14, fontWeight: 500, marginBottom: 10 }}>{c.courseName}</p>
                  <Btn primary small disabled={enrolling === c.courseId} onClick={() => enroll(c.courseId)}>
                    {enrolling === c.courseId ? "Enrolling..." : "Enroll"}
                  </Btn>
                </Card>
              )) : <p style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>No more courses available.</p>}
            </div>
          </>
        )}

        <div style={{ marginTop: "2rem", background: "var(--color-background-secondary)", borderRadius: "var(--border-radius-md)", padding: 12, fontSize: 12, color: "var(--color-text-secondary)" }}>
          <p style={{ fontWeight: 500, color: "var(--color-text-primary)", marginBottom: 4 }}>API calls on this page:</p>
          <p>GET /courses → all courses (Authorization: Bearer token)</p>
          <p>GET /students/me/courses → my enrolled courses</p>
          <p>POST /students/me/enroll/{"{courseId}"} → enroll</p>
        </div>
      </div>
    </div>
  );
}

// ─── Admin Dashboard ───────────────────────────────────────────────

function AdminPage({ token, onLogout }) {
  const [courses, setCourses]         = useState([]);
  const [newCourseName, setNewCourse] = useState("");
  const [selectedCourse, setSelected] = useState("");
  const [students, setStudents]       = useState([]);
  const [addMsg, setAddMsg]           = useState("");
  const [loading, setLoading]         = useState(true);

  async function loadCourses() {
    setLoading(true);
    const res = await fetch(`${BASE}/courses`, { headers: authHeaders(token) });
    const data = await res.json();
    setCourses(data);
    if (data.length) { setSelected(String(data[0].courseId)); }
    setLoading(false);
  }

  useEffect(() => { loadCourses(); }, []);

  useEffect(() => {
    if (!selectedCourse) return;
    fetch(`${BASE}/courses/${selectedCourse}/students`, { headers: authHeaders(token) })
      .then(r => r.json()).then(setStudents).catch(() => setStudents([]));
  }, [selectedCourse]);

  async function addCourse() {
    if (!newCourseName.trim()) return;
    await fetch(`${BASE}/courses`, { method: "POST", headers: authHeaders(token), body: JSON.stringify({ courseName: newCourseName }) });
    setNewCourseName("");
    setAddMsg("Course added!");
    setTimeout(() => setAddMsg(""), 2000);
    loadCourses();
  }

  const inputStyle = {
    width: "100%", padding: "8px 12px", fontSize: 14,
    border: "0.5px solid var(--color-border-secondary)",
    borderRadius: "var(--border-radius-md)",
    background: "var(--color-background-primary)",
    color: "var(--color-text-primary)", fontFamily: "var(--font-sans)",
    marginBottom: 10,
  };

  return (
    <div style={{ minHeight: "100vh", background: "var(--color-background-tertiary)" }}>
      <Navbar title="Student Portal" role="ROLE_ADMIN" onLogout={onLogout} />
      <div style={{ maxWidth: 860, margin: "0 auto", padding: "1.5rem" }}>
        {loading ? <p style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>Loading...</p> : (
          <>
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "1.5rem", marginBottom: "1.5rem" }}>
              <Card>
                <h3 style={{ fontSize: 15, fontWeight: 500, marginBottom: "1rem" }}>Add new course</h3>
                <label style={{ fontSize: 13, color: "var(--color-text-secondary)", display: "block", marginBottom: 6 }}>Course name</label>
                <input style={inputStyle} value={newCourseName} onChange={e => setNewCourse(e.target.value)}
                  placeholder="e.g. Spring Security" onKeyDown={e => e.key === "Enter" && addCourse()} />
                <Btn primary small fullWidth onClick={addCourse}>Add course</Btn>
                {addMsg && <p style={{ fontSize: 13, color: "var(--color-text-success)", textAlign: "center", marginTop: 8 }}>{addMsg}</p>}
              </Card>

              <Card>
                <h3 style={{ fontSize: 15, fontWeight: 500, marginBottom: "1rem" }}>Students in course</h3>
                <select value={selectedCourse} onChange={e => setSelected(e.target.value)} style={{ ...inputStyle, cursor: "pointer" }}>
                  {courses.map(c => <option key={c.courseId} value={c.courseId}>{c.courseName}</option>)}
                </select>
                <div style={{ display: "flex", flexDirection: "column", gap: 6, maxHeight: 160, overflowY: "auto" }}>
                  {students.length ? students.map(s => (
                    <div key={s.studentId} style={{ display: "flex", alignItems: "center", gap: 8, padding: "6px 10px", background: "var(--color-background-secondary)", borderRadius: "var(--border-radius-md)" }}>
                      <div style={{ width: 26, height: 26, borderRadius: "50%", background: "var(--color-background-info)", display: "flex", alignItems: "center", justifyContent: "center", fontSize: 11, fontWeight: 500, color: "var(--color-text-info)" }}>
                        {s.studentName?.[0] || "?"}
                      </div>
                      <div>
                        <p style={{ fontSize: 13, fontWeight: 500 }}>{s.studentName}</p>
                        <p style={{ fontSize: 12, color: "var(--color-text-secondary)" }}>{s.email}</p>
                      </div>
                    </div>
                  )) : <p style={{ fontSize: 13, color: "var(--color-text-secondary)" }}>No students enrolled yet.</p>}
                </div>
              </Card>
            </div>

            <Card>
              <h3 style={{ fontSize: 15, fontWeight: 500, marginBottom: "1rem" }}>All courses</h3>
              <div style={{ display: "flex", flexDirection: "column", gap: 8 }}>
                {courses.map(c => (
                  <div key={c.courseId} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "8px 0", borderBottom: "0.5px solid var(--color-border-tertiary)" }}>
                    <span style={{ fontSize: 14 }}>{c.courseName}</span>
                    <Badge type="info">ID: {c.courseId}</Badge>
                  </div>
                ))}
              </div>
            </Card>
          </>
        )}

        <div style={{ marginTop: "1.5rem", background: "var(--color-background-secondary)", borderRadius: "var(--border-radius-md)", padding: 12, fontSize: 12, color: "var(--color-text-secondary)" }}>
          <p style={{ fontWeight: 500, color: "var(--color-text-primary)", marginBottom: 4 }}>API calls on this page:</p>
          <p>GET /courses → all courses (Authorization: Bearer token)</p>
          <p>POST /courses → add new course (ROLE_ADMIN only)</p>
          <p>GET /courses/{"{id}"}/students → enrolled students</p>
        </div>
      </div>
    </div>
  );
}

// ─── App Root ──────────────────────────────────────────────────────

export default function App() {
  const [token, setToken] = useState(() => localStorage.getItem("jwt_token"));
  const [role,  setRole]  = useState(() => localStorage.getItem("jwt_role"));

  function handleLogin(t, r) { setToken(t); setRole(r); }

  function handleLogout() {
    localStorage.removeItem("jwt_token");
    localStorage.removeItem("jwt_role");
    setToken(null); setRole(null);
  }
  
  if (!token) return <LoginPage onLogin={handleLogin} />;
  if (role === "ROLE_ADMIN") return <AdminPage token={token} onLogout={handleLogout} />;
  return <StudentPage token={token} onLogout={handleLogout} />;
}